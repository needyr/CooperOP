$.fn.extend({
	"pdfviewer" : function(option) {
		var $this = this;
		if (!option) {
			if ($this.data("pdfviewer")) {
				return $this.data("pdfviewer");
			} else {
				throw new Exception("PDFViewer is not initiated.");
			}
		}

		var CSS_UNITS = 96.0 / 72.0;
		var DEFAULT_SCALE = 'auto';
		var UNKNOWN_SCALE = 0;
		var MAX_AUTO_SCALE = 1.25;
		function getFileName(url) {
			var anchor = url.indexOf('#');
			var query = url.indexOf('?');
			var end = Math.min(anchor > 0 ? anchor : url.length, query > 0 ? query : url.length);
			return url.substring(url.lastIndexOf('/', end) + 1, end);
		}

		var pdfviewer = {
			"option" : $.extend(true, {
				pagewrap : null,
				pagetop : 0,
				thumbwrap : null,
				thumbwidth : 130,
				outlinewrap : null,
				attachmentwrap : null,
				progressbar : null,

				ondrawpageover : function(page) {
					console.log("page");
					console.log(page);
				},
				ondrawthumbsover : function(thumbs) {
					console.log("thumbs");
					console.log(thumbs);
				},
				ondrawoutlineover : function(outline) {
					console.log("outline");
					console.log(outline);
				},
				ondrawattachmentsover : function(attachments) {
					console.log("attachments");
					console.log(attachments);
				},

				onload : function(pdfdoc) {
					console.log(pdfdoc);
				},
				passwordprompt : function(reason, callback) {
					console.log(reason);
					callback('');
				}

			}, option),
			"pdfdoc" : {
				filename : null,
				document : null,
				info : null,
				metadata : null,
				pagecount : 0,
				loadedpagecount : 0,
				thumbs : [],
				pages : [],
				outline : [],
				attachments : [],
				scale : 1.0,
				rotation : 0,
				currentpage : 0
			},
			"open" : function(file, filename) {
				if (file instanceof File) {
					var fn = file.name;
					if (fn.indexOf(".") > -1) {
						fn = fn.substring(0, fn.lastIndexOf("."));
					}
					pdfviewer.pdfdoc.filename = filename || fn;

					if (typeof URL !== 'undefined' && URL.createObjectURL) {
						pdfviewer.loadFile(URL.createObjectURL(file), 0);
					} else {
						var fileReader = new FileReader();
						fileReader.onload = function webViewerChangeFileReaderOnload(evt) {
							var buffer = evt.target.result;
							var uint8Array = new Uint8Array(buffer);
							pdfviewer.loadFile(uint8Array, 0);
						};
						fileReader.readAsArrayBuffer(file);
					}
				} else if (typeof file === 'string') {
					pdfviewer.pdfdoc.filename = filename || (decodeURIComponent(getFileName(file)) || file);
					var xhr = new XMLHttpRequest();
					xhr.onload = function() {
						pdfviewer.loadFile(new Uint8Array(xhr.response), 0);
					};
					try {
						xhr.open('GET', file);
						xhr.responseType = 'arraybuffer';
						xhr.send();
					} catch (e) {
						throw new Exception('An error occurred while loading the PDF.', e);
					}
				}
			},
			"closeFile" : function() {
				if (pdfviewer.option["pagewrap"]) {
					pdfviewer.option["pagewrap"].html("");
					pdfviewer.option["pagewrap"].off("scroll");
				}
				if (pdfviewer.option["thumbwrap"]) {
					pdfviewer.option["thumbwrap"].html("");
				}
				if (pdfviewer.option["outlinewrap"]) {
					pdfviewer.option["outlinewrap"].html("");
				}
				if (pdfviewer.option["attachmentwrap"]) {
					pdfviewer.option["attachmentwrap"].html("");
				}
				pdfviewer.pdfdoc = {
					filename : null,
					document : null,
					info : null,
					metadata : null,
					pagecount : 0,
					loadedpagecount : 0,
					thumbs : [],
					pages : [],
					outline : [],
					attachments : [],
					scale : 1.0,
					rotation : 0,
					currentpage : 0
				}
			},
			"loadFile" : function(file, scale, password, pdfDataRangeTransport, args) {
				var parameters = {
					password : password
				};
				if (typeof file === 'string') { // URL
					parameters.url = file;
				} else if (file && 'byteLength' in file) { // ArrayBuffer
					parameters.data = file;
				} else if (file.url && file.originalUrl) {
					parameters.url = file.url;
				}
				PDFJS.getDocument(parameters, pdfDataRangeTransport, function passwordNeeded(updatePassword, reason) {
					this.updatePassword = updatePassword;
					this.reason = reason;
					if (pdfviewer.option["passwordprompt"]) {
						pdfviewer.option["passwordprompt"](reason, function(pwd) {
							updatePassword(pwd);
						});
					}
				}, function(progressData) {
					if (pdfviewer.option["loadprogress"]) {
						pdfviewer.option["loadprogress"](progressData.loaded, progressData.total);
					}
				}).then(function(pdfDocument) {
					if (pdfviewer.pdfdoc.document && pdfDocument.fingerprint == pdfviewer.pdfdoc.document.fingerprint)
						return;
					var filename = pdfviewer.pdfdoc.filename;
					pdfviewer.closeFile();
					pdfviewer.pdfdoc.filename = filename;
					pdfviewer.pdfdoc.document = pdfDocument;
					pdfviewer.pdfdoc.pagecount = pdfviewer.pdfdoc.document.numPages;

					pdfviewer.pdfdoc.document.getMetadata().then(function(data) {
						pdfviewer.pdfdoc.info = data.info;
						pdfviewer.pdfdoc.metadata = data.metadata;
						var info = data.info, metadata = data.metadata;
						var pdfTitle;
						if (metadata && metadata.has('dc:title')) {
							var title = metadata.get('dc:title');
							if (title !== 'Untitled') {
								pdfTitle = title;
							}
						}
						if (!pdfTitle && info && info['Title']) {
							pdfTitle = info['Title'];
						}

						if (pdfTitle) {
							pdfviewer.pdfdoc.filename = pdfTitle || pdfviewer.pdfdoc.filename;
						}

					});

					function loadOutline() {
						pdfviewer.pdfdoc.document.getOutline().then(function(outline) {
							pdfviewer.pdfdoc.outline = outline;
							var dr = function(o, p) {
								var ol = [];
								var div = $('<ul class="pdf-outline"></ul>');
								p.append(div);

								if (p == pdfviewer.option.outlinewrap) {
									div.css("position", "relative");
								}

								function draw(d) {
									for ( var i in pdfviewer.pdfdoc.pages) {
										if (d.dest[0].num == pdfviewer.pdfdoc.pages[i].refnum) {
											d.pagenum = pdfviewer.pdfdoc.pages[i].pagenum;
											break;
										}
									}
									var li = $([ '<li><a href="javascript:void(0)">', d.title, '<span>', d.pagenum, '</span></a></li>' ].join(""));
									li.find("a").data("outline", d);
									li.find("a").click(function() {
										pdfviewer.navigateTo(d.pagenum, d.dest);
									});
									div.append(li);
									d.children = dr(o[c].items, li);
								}

								for ( var c in o) {
									draw(o[c]);
									var d = {
										title : o[c].title,
										dest : o[c].dest,
										pagenum : o[c].pagenum
									};
									ol.push(d);
								}
								return ol;
							};

							var ol = [];
							if (pdfviewer.option["outlinewrap"]) {
								pdfviewer.option.outlinewrap.html("");
								if (outline && outline.length > 0) {
									ol = dr(outline, pdfviewer.option.outlinewrap);
								}
							}
							if (pdfviewer.option["ondrawoutlineover"]) {
								pdfviewer.option["ondrawoutlineover"](ol);
							}
						});
					}

					function loadAttachments() {
						pdfviewer.pdfdoc.document.getAttachments().then(function(attachments) {
							pdfviewer.pdfdoc.attachments = attachments;
							var ats = [];
							if (attachments && attachments.length > 0 && pdfviewer.option["attachmentwrap"]) {
								var ol = [];
								var div = $('<ul class="pdf-attachments"></ul>');
								pdfviewer.option["attachmentwrap"].append(div);
								function draw(a) {
									for ( var o in outline) {
										var li = $([ '<li><a href="javascript:void(0)"><i class="fa fa-file-o"></i><span>', a.filename, '</span></a></li>' ].join(""));
										li.find("a").click(function() {
											if (navigator.msSaveBlob) { // IE10
												// and
												// above
												return navigator.msSaveBlob(new Blob([ a.content ], {
													type : ''
												}), a.filename);
											}

											var blobUrl = PDFJS.createObjectURL(a.content, '');
											download(blobUrl, a.filename);
										});
										div.append(li);
									}
								}
								var names = Object.keys(attachments).sort(function(a, b) {
									return a.toLowerCase().localeCompare(b.toLowerCase());
								});
								for (var i = 0; i < names.length; i++) {
									var item = attachments[names[i]];
									var a = {
										filename : getFileName(item.filename),
										content : item.content
									}
									draw(a);
									ats.push(a);
								}
							}
							if (pdfviewer.option["ondrawattachmentsover"]) {
								pdfviewer.option["ondrawattachmentsover"](ats);
							}
						});
					}

					var loadPagesOver = function() {
						if (pdfviewer.pdfdoc.loadedpagecount == pdfviewer.pdfdoc.pagecount) {
							loadOutline();
							loadAttachments();
							pdfviewer.repaintThumbs();
							pdfviewer.repaintPages();
							if (pdfviewer.option["pagewrap"]) {
								pdfviewer.option["pagewrap"].on("scroll", function(evt) {
									var p = 1;
									for (var pn = 0; pn < pdfviewer.pdfdoc.pagecount; pn++) {
										var page = pdfviewer.pdfdoc.pages[pn];
										if (page.eleobj.position().top + page.height - pdfviewer.option["pagewrap"].height() / 2 - pdfviewer.option["pagewrap"].scrollTop() >= 0) {
											p = page.pagenum;
											break;
										}
									}
									pdfviewer.setCurrentPage(p);

									if (pdfviewer.option["outlinewrap"]) {
										if (pdfviewer.pdfdoc.outline && pdfviewer.pdfdoc.outline.length > 0) {
											var ol = [];
											function d(o) {
												for ( var i in o) {
													ol.push(o[i]);
													d(o[i].items);
												}
											}

											d(pdfviewer.pdfdoc.outline);
											var parent = pdfviewer.option["pagewrap"];
											var o;
											for ( var i in ol) {
												var top = page.viewport.convertToViewportPoint(ol[i].dest[2], ol[i].dest[3])[1];
												var page = pdfviewer.pdfdoc.pages[ol[i].pagenum - 1];
												if (parent.scrollTop() - top - page.eleobj.position().top >= 0) {
													o = ol[i];
												} else {
													break;
												}
											}

											if (o) {
												pdfviewer.option["outlinewrap"].find("a").removeClass("selected");
												pdfviewer.option["outlinewrap"].find("a").each(function(index, a) {
													if ($(a).data("outline") == o) {
														$(a).addClass("selected");
														var t = $(a).parent().position().top + $(a).parent().height() / 2 - pdfviewer.option["outlinewrap"].height() / 2;
														pdfviewer.option["outlinewrap"].scrollTop(t);
														return false;
													}
												});
											}
										}
									}
								});
							}
							if (pdfviewer.option["onload"]) {
								pdfviewer.option["onload"]({
									filename : pdfviewer.pdfdoc.filename,
									info : pdfviewer.pdfdoc.info,
									metadata : pdfviewer.pdfdoc.metadata,
									pagecount : pdfviewer.pdfdoc.pagecount
								});
							}
						}
					}
					var loadPages = function() {
						for (var pageNum = 1; pageNum <= pdfviewer.pdfdoc.pagecount; ++pageNum) {
							pdfviewer.pdfdoc.document.getPage(pageNum).then(function(pdfPage) {
								var scale = 1.0;
								var viewport = pdfPage.getViewport(scale * CSS_UNITS);
								var w = Math.floor(viewport.width), h = Math.floor(viewport.height);
								var scaleT = pdfviewer.option.thumbwidth / w * scale;
								var viewportT = pdfPage.getViewport(scaleT * CSS_UNITS);
								var tw = Math.floor(viewportT.width), th = Math.floor(viewportT.height);
								pdfviewer.pdfdoc.thumbs[pdfPage.pageNumber - 1] = {
									pdfpage : pdfPage,
									scale : scaleT,
									viewport : viewportT,
									pagenum : pdfPage.pageNumber,
									refnum : pdfPage.pageInfo.ref.num,
									width : tw,
									height : th
								};

								var vw = pdfviewer.option.pagewrap.width();
								if (w > vw) {
									scale = vw / w;
								}
								pdfviewer.pdfdoc.scale = pdfviewer.pdfdoc.scale > scale ? scale : pdfviewer.pdfdoc.scale;

								pdfviewer.pdfdoc.pages[pdfPage.pageNumber - 1] = {
									pdfpage : pdfPage,
									viewport : null,
									pagenum : pdfPage.pageNumber,
									refnum : pdfPage.pageInfo.ref.num,
									srcwidth : w,
									srcheight : h
								};
								pdfviewer.pdfdoc.loadedpagecount++;
								loadPagesOver();
							});
						}
					}

					loadPages();

				}, function getDocumentError(exception) {
					var message = exception && exception.message;
					var loadingErrorMessage = 'An error occurred while loading the PDF.';

					if (exception instanceof PDFJS.InvalidPDFException) {
						// change error message also for
						// other builds
						loadingErrorMessage = 'Invalid or corrupted PDF file.';
					} else if (exception instanceof PDFJS.MissingPDFException) {
						// special message for missing
						// PDF's
						loadingErrorMessage = 'Missing PDF file.';
					} else if (exception instanceof PDFJS.UnexpectedResponseException) {
						loadingErrorMessage = 'Unexpected server response.';
					}

					var moreInfo = {
						message : message
					};

					console.log(loadingErrorMessage);
					console.log(moreInfo);
				});
			},
			"render" : function(page) {

			},
			"setCurrentPage" : function(page) {
				pdfviewer.pdfdoc.currentpage = page;
				var e = pdfviewer.pdfdoc.thumbs[page - 1].eleobj;
				if (pdfviewer.option["thumbwrap"]) {
					pdfviewer.option["thumbwrap"].find(".pdf-thumb > a").removeClass("selected");
					e.children('a').addClass("selected");
					var t = e.position().top + e.height() / 2 - pdfviewer.option["thumbwrap"].height() / 2;
					pdfviewer.option["thumbwrap"].scrollTop(t);
				}
			},
			"navigateTo" : function(page, dest) {
				var page = pdfviewer.pdfdoc.pages[page - 1];
				if (dest) {
					var x = 0, y = 0;
					var spot = undefined;
					var width = 0, height = 0, widthScale, heightScale;
					var changeOrientation = (pdfviewer.pdfdoc.rotation % 180 === 0 ? false : true);
					var pageWidth = (changeOrientation ? page.height : page.width) / pdfviewer.pdfdoc.scale / CSS_UNITS;
					var pageHeight = (changeOrientation ? page.width : page.height) / pdfviewer.pdfdoc.scale / CSS_UNITS;
					var scale = 0;
					switch (dest[1].name) {
					case 'XYZ':
						x = dest[2];
						y = dest[3];
						scale = dest[4];
						x = x !== null ? x : 0;
						y = y !== null ? y : pageHeight;
						break;
					case 'Fit':
					case 'FitB':
						scale = 'page-fit';
						break;
					case 'FitH':
					case 'FitBH':
						y = dest[2];
						scale = 'page-width';
						break;
					case 'FitV':
					case 'FitBV':
						x = dest[2];
						width = pageWidth;
						height = pageHeight;
						scale = 'page-height';
						break;
					case 'FitR':
						x = dest[2];
						y = dest[3];
						width = dest[4] - x;
						height = dest[5] - y;
						var viewerContainer = this.container;
						var hPadding = /* this.removePageBorders */false ? 0 : SCROLLBAR_PADDING;
						var vPadding = /* this.removePageBorders */false ? 0 : VERTICAL_PADDING;

						widthScale = ($(".pdfviewer .pdf-document").width() - hPadding) / width / CSS_UNITS;
						heightScale = ($(".pdfviewer .pdf-document").height() - vPadding) / height / CSS_UNITS;
						scale = Math.min(Math.abs(widthScale), Math.abs(heightScale));
						break;
					default:
						return;
					}

					/*
					 * if (scale && scale !== this.currentScale) {
					 * this.currentScaleValue = scale; } else if
					 * (this.currentScale === UNKNOWN_SCALE) {
					 * this.currentScaleValue = DEFAULT_SCALE; }
					 */

					if (scale === 'page-fit' && !dest[4]) {
					} else {
						var boundingRect = [ page.viewport.convertToViewportPoint(x, y), page.viewport.convertToViewportPoint(x + width, y + height) ];
						var left = Math.min(boundingRect[0][0], boundingRect[1][0]);
						var top = Math.min(boundingRect[0][1], boundingRect[1][1]);
						spot = {
							left : left,
							top : top
						};
					}
				}

				if (pdfviewer.option["pagewrap"]) {
					if (page.height - pdfviewer.option["pagewrap"].height() / 2 + page.eleobj.position().top >= 0) {
						pdfviewer.setCurrentPage(page.pagenum);
					} else {
						pdfviewer.setCurrentPage(page.pagenum + 1);
					}
					var parent = pdfviewer.option["pagewrap"];
					var offsetY = page.eleobj.position().top;
					var offsetX = page.eleobj.position().left;
					if (!parent) {
						console.error('offsetParent is not set -- cannot scroll');
						return;
					}
					while (parent.height() === parent[0].scrollHeight) {
						offsetY += parent.position().top;
						offsetX += parent.position().left;
						parent = parent.parent();
						if (parent.length == 0) {
							return; // no need to scroll
						}
					}
					if (spot) {
						if (spot.top !== undefined) {
							offsetY += spot.top;
						}
						if (spot.left !== undefined) {
							offsetX += spot.left;
							parent.scrollLeft(offsetX);
						}
					}
					parent.scrollTop(offsetY);
				}
			},
			"zoom" : function(zoom) { // 放大：in，
				// 缩小：out，最佳大小：best，原始大小：nature
				if (zoom == "in") {
					pdfviewer.pdfdoc.scale *= 1.25;
					pdfviewer.repaintPages();
				} else if (zoom == "out") {
					pdfviewer.pdfdoc.scale /= 1.25;
					pdfviewer.repaintPages();
				} else if (zoom == "best") {
					var s = 1.0;
					for (var pageNum = 1; pageNum <= pdfviewer.pdfdoc.pagecount; ++pageNum) {
						var scale = 1.0;
						var vw = pdfviewer.option.pagewrap.width();
						if (pdfviewer.pdfdoc.pages[pageNum - 1].srcwitdh > vw) {
							scale = pdfviewer.pdfdoc.pages[pageNum - 1].srcwitdh / w;
						}
						s = s > scale ? scale : s;
					}
					pdfviewer.pdfdoc.scale = s;
					pdfviewer.repaintPages();
				} else if (zoom == "nature") {
					pdfviewer.pdfdoc.scale = 1.0;
					pdfviewer.repaintPages();
				} else {
					return;
				}
			},
			"repaintThumbs" : function() {
				if (pdfviewer.option["thumbwrap"]) {
					pdfviewer.option["thumbwrap"].html("");
					setTimeout(function() {
						var t = [];
						var div = $('<ul class="pdf-thumbnails" style="position:relative;"></ul>');
						pdfviewer.option["thumbwrap"].append(div);

						function drawThumb(thumb) {
							var li = $([ '<li class="pdf-thumb"><a href="javascript:void(0)">', '<canvas width="', thumb.width, '" height="', thumb.height, '" style="width:', thumb.width,
									'px;height:', thumb.height, 'px;"></canvas><span>', thumb.pagenum, '/', pdfviewer.pdfdoc.pagecount, '</span></a></li>' ].join(""));
							li.find("a").click(function() {
								pdfviewer.navigateTo(thumb.pagenum);
							});
							thumb.eleobj = li;
							setTimeout(function() {
								thumb.pdfpage.render({
									canvasContext : li.find("canvas")[0].getContext('2d'),
									viewport : thumb.viewport,
									// intent: 'default', // ====
									// 'display'
									continueCallback : function(continueF) {
										continueF();
									}
								});
							}, 100);
							div.append(li);
						}

						for (var i = 0; i < pdfviewer.pdfdoc.pagecount; i++) {
							drawThumb(pdfviewer.pdfdoc.thumbs[i]);
							t.push({
								eleobj : pdfviewer.pdfdoc.thumbs[i].eleobj,
								scale : pdfviewer.pdfdoc.thumbs[i].scale,
								pagenum : pdfviewer.pdfdoc.thumbs[i].pagenum,
								refnum : pdfviewer.pdfdoc.thumbs[i].refnum
							});
						}
						pdfviewer.setCurrentPage(1);

						if (pdfviewer.option["ondrawthumbsover"]) {
							pdfviewer.option["ondrawthumbsover"](t);
						}
					}, 0);
				}
			},
			"repaintPages" : function() {
				if (pdfviewer.option["pagewrap"]) {
					pdfviewer.option["pagewrap"].html("");
					setTimeout(function() {
						var pdiv = $('<ul class="pdf-pages" style="position:relative;"></ul>');
						pdfviewer.option["pagewrap"].append(pdiv);

						function drawPage(page) {
							page.viewport = page.pdfpage.getViewport(pdfviewer.pdfdoc.scale * CSS_UNITS);
							page.width = Math.floor(page.viewport.width);
							page.height = Math.floor(page.viewport.height);
							var li = $([ '<li class="pdf-page" style="width:', page.width, 'px;height:', page.height, 'px;">', '<canvas width="', page.width, '" height="', page.height,
									'" style="width:', page.width, 'px;height:', page.height,
									'px;"></canvas><div class="pdf-pageloading"></div><div class="pdf-textlayer"></div><div class="pdf-colorlayer"></div></li>' ].join(""));
							page.eleobj = li;
							pdiv.append(li);
							setTimeout(function() {
								page.pdfpage.render({
									canvasContext : page.eleobj.find("canvas")[0].getContext('2d'),
									viewport : page.viewport,
									// intent: 'default', //
									// ====
									// 'display'
									continueCallback : function(continueF) {
										page.eleobj.find(".pdf-pageloading").hide();
										continueF();
									}
								});
							}, 100);
						}

						for (var i = 0; i < pdfviewer.pdfdoc.pagecount; i++) {
							drawPage(pdfviewer.pdfdoc.pages[i]);
							if (pdfviewer.option["ondrawpageover"]) {
								pdfviewer.option["ondrawpageover"]({
									eleobj : pdfviewer.pdfdoc.pages[i].eleobj,
									scale : pdfviewer.pdfdoc.scale,
									pagenum : pdfviewer.pdfdoc.pages[i].pagenum,
									refnum : pdfviewer.pdfdoc.pages[i].refnum
								});
							}
						}

					}, 0);
				}
			},
			"gopage" : function(page) {
				if (typeof page === "string") {
					if (page == "first") {
						page = 1;
					} else if (page == "prev") {
						page = pdfviewer.pdfdoc.currentpage - 1;
					} else if (page == "next") {
						page = pdfviewer.pdfdoc.currentpage + 1;
					} else if (page == "last") {
						page = pdfviewer.pdfdoc.pagecount;
					}
				}
				if (+page <= 0)
					page = 1;
				if (+page > pdfviewer.pdfdoc.pagecount)
					page = pdfviewer.pdfdoc.pagecount;
				pdfviewer.navigateTo(+page);
			},
			"rotate": function(degree) {
				pdfviewer.pdfdoc.rotation = (pdfviewer.pdfdoc.rotation + degree) % 360;
				console.log(pdfviewer.pdfdoc.rotation);
			}
		};

		$this.data("pdfviewer", pdfviewer);

		return $this.data("pdfviewer");
	}
});