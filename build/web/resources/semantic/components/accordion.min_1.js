/*!
 * # Semantic UI 2.1.7 - Accordion
 * http://github.com/semantic-org/semantic-ui/
 *
 *
 * Copyright 2015 Contributors
 * Released under the MIT license
 * http://opensource.org/licenses/MIT
 *
 */
!function(e,n,t,i){"use strict";e.fn.accordion=function(t){{var o,a=e(this),s=(new Date).getTime(),r=[],c=arguments[0],l="string"==typeof c,u=[].slice.call(arguments,1);n.requestAnimationFrame||n.mozRequestAnimationFrame||n.webkitRequestAnimationFrame||n.msRequestAnimationFrame||function(e){setTimeout(e,0)}}return a.each(function(){var d,g,m=e.isPlainObject(t)?e.extend(!0,{},e.fn.accordion.settings,t):e.extend({},e.fn.accordion.settings),f=m.className,p=m.namespace,v=m.selector,b=m.error,h="."+p,y="module-"+p,C=a.selector||"",O=e(this),x=O.find(v.title),A=O.find(v.content),F=this,T=O.data(y);g={initialize:function(){g.debug("Initializing",O),g.bind.events(),m.observeChanges&&g.observeChanges(),g.instantiate()},instantiate:function(){T=g,O.data(y,g)},destroy:function(){g.debug("Destroying previous instance",O),O.off(h).removeData(y)},refresh:function(){x=O.find(v.title),A=O.find(v.content)},observeChanges:function(){"MutationObserver"in n&&(d=new MutationObserver(function(e){g.debug("DOM tree modified, updating selector cache"),g.refresh()}),d.observe(F,{childList:!0,subtree:!0}),g.debug("Setting up mutation observer",d))},bind:{events:function(){g.debug("Binding delegated events"),O.on(m.on+h,v.trigger,g.event.click)}},event:{click:function(){g.toggle.call(this)}},toggle:function(n){var t=n!==i?"number"==typeof n?x.eq(n):e(n).closest(v.title):e(this).closest(v.title),o=t.next(A),a=o.hasClass(f.animating),s=o.hasClass(f.active),r=s&&!a,c=!s&&a;g.debug("Toggling visibility of content",t),r||c?m.collapsible?g.close.call(t):g.debug("Cannot close accordion content collapsing is disabled"):g.open.call(t)},open:function(n){var t=n!==i?"number"==typeof n?x.eq(n):e(n).closest(v.title):e(this).closest(v.title),o=t.next(A),a=o.hasClass(f.animating),s=o.hasClass(f.active),r=s||a;return r?void g.debug("Accordion already open, skipping",o):(g.debug("Opening accordion content",t),m.onOpening.call(o),m.exclusive&&g.closeOthers.call(t),t.addClass(f.active),o.stop(!0,!0).addClass(f.animating),m.animateChildren&&(e.fn.transition!==i&&O.transition("is supported")?o.children().transition({animation:"fade in",queue:!1,useFailSafe:!0,debug:m.debug,verbose:m.verbose,duration:m.duration}):o.children().stop(!0,!0).animate({opacity:1},m.duration,g.resetOpacity)),void o.slideDown(m.duration,m.easing,function(){o.removeClass(f.animating).addClass(f.active),g.reset.display.call(this),m.onOpen.call(this),m.onChange.call(this)}))},close:function(n){var t=n!==i?"number"==typeof n?x.eq(n):e(n).closest(v.title):e(this).closest(v.title),o=t.next(A),a=o.hasClass(f.animating),s=o.hasClass(f.active),r=!s&&a,c=s&&a;!s&&!r||c||(g.debug("Closing accordion content",o),m.onClosing.call(o),t.removeClass(f.active),o.stop(!0,!0).addClass(f.animating),m.animateChildren&&(e.fn.transition!==i&&O.transition("is supported")?o.children().transition({animation:"fade out",queue:!1,useFailSafe:!0,debug:m.debug,verbose:m.verbose,duration:m.duration}):o.children().stop(!0,!0).animate({opacity:0},m.duration,g.resetOpacity)),o.slideUp(m.duration,m.easing,function(){o.removeClass(f.animating).removeClass(f.active),g.reset.display.call(this),m.onClose.call(this),m.onChange.call(this)}))},closeOthers:function(n){var t,o,a,s=n!==i?x.eq(n):e(this).closest(v.title),r=s.parents(v.content).prev(v.title),c=s.closest(v.accordion),l=v.title+"."+f.active+":visible",u=v.content+"."+f.active+":visible";m.closeNested?(t=c.find(l).not(r),a=t.next(A)):(t=c.find(l).not(r),o=c.find(u).find(l).not(r),t=t.not(o),a=t.next(A)),t.length>0&&(g.debug("Exclusive enabled, closing other content",t),t.removeClass(f.active),a.removeClass(f.animating).stop(!0,!0),m.animateChildren&&(e.fn.transition!==i&&O.transition("is supported")?a.children().transition({animation:"fade out",useFailSafe:!0,debug:m.debug,verbose:m.verbose,duration:m.duration}):a.children().stop(!0,!0).animate({opacity:0},m.duration,g.resetOpacity)),a.slideUp(m.duration,m.easing,function(){e(this).removeClass(f.active),g.reset.display.call(this)}))},reset:{display:function(){g.verbose("Removing inline display from element",this),e(this).css("display",""),""===e(this).attr("style")&&e(this).attr("style","").removeAttr("style")},opacity:function(){g.verbose("Removing inline opacity from element",this),e(this).css("opacity",""),""===e(this).attr("style")&&e(this).attr("style","").removeAttr("style")}},setting:function(n,t){if(g.debug("Changing setting",n,t),e.isPlainObject(n))e.extend(!0,m,n);else{if(t===i)return m[n];m[n]=t}},internal:function(n,t){return g.debug("Changing internal",n,t),t===i?g[n]:void(e.isPlainObject(n)?e.extend(!0,g,n):g[n]=t)},debug:function(){m.debug&&(m.performance?g.performance.log(arguments):(g.debug=Function.prototype.bind.call(console.info,console,m.name+":"),g.debug.apply(console,arguments)))},verbose:function(){m.verbose&&m.debug&&(m.performance?g.performance.log(arguments):(g.verbose=Function.prototype.bind.call(console.info,console,m.name+":"),g.verbose.apply(console,arguments)))},error:function(){g.error=Function.prototype.bind.call(console.error,console,m.name+":"),g.error.apply(console,arguments)},performance:{log:function(e){var n,t,i;m.performance&&(n=(new Date).getTime(),i=s||n,t=n-i,s=n,r.push({Name:e[0],Arguments:[].slice.call(e,1)||"",Element:F,"Execution Time":t})),clearTimeout(g.performance.timer),g.performance.timer=setTimeout(g.performance.display,500)},display:function(){var n=m.name+":",t=0;s=!1,clearTimeout(g.performance.timer),e.each(r,function(e,n){t+=n["Execution Time"]}),n+=" "+t+"ms",C&&(n+=" '"+C+"'"),(console.group!==i||console.table!==i)&&r.length>0&&(console.groupCollapsed(n),console.table?console.table(r):e.each(r,function(e,n){console.log(n.Name+": "+n["Execution Time"]+"ms")}),console.groupEnd()),r=[]}},invoke:function(n,t,a){var s,r,c,l=T;return t=t||u,a=F||a,"string"==typeof n&&l!==i&&(n=n.split(/[\. ]/),s=n.length-1,e.each(n,function(t,o){var a=t!=s?o+n[t+1].charAt(0).toUpperCase()+n[t+1].slice(1):n;if(e.isPlainObject(l[a])&&t!=s)l=l[a];else{if(l[a]!==i)return r=l[a],!1;if(!e.isPlainObject(l[o])||t==s)return l[o]!==i?(r=l[o],!1):(g.error(b.method,n),!1);l=l[o]}})),e.isFunction(r)?c=r.apply(a,t):r!==i&&(c=r),e.isArray(o)?o.push(c):o!==i?o=[o,c]:c!==i&&(o=c),r}},l?(T===i&&g.initialize(),g.invoke(c)):(T!==i&&T.invoke("destroy"),g.initialize())}),o!==i?o:this},e.fn.accordion.settings={name:"Accordion",namespace:"accordion",debug:!1,verbose:!1,performance:!0,on:"click",observeChanges:!0,exclusive:!0,collapsible:!0,closeNested:!1,animateChildren:!0,duration:350,easing:"easeOutQuad",onOpening:function(){},onOpen:function(){},onClosing:function(){},onClose:function(){},onChange:function(){},error:{method:"The method you called is not defined"},className:{active:"active",animating:"animating"},selector:{accordion:".accordion",title:".title",trigger:".title",content:".content"}},e.extend(e.easing,{easeOutQuad:function(e,n,t,i,o){return-i*(n/=o)*(n-2)+t}})}(jQuery,window,document);