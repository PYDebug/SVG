var downList = function() {	
	
		window.prettyPrint && prettyPrint();
		
		$('#dp1').datepicker($('#dp1'),{
			format : 'mm-dd-yyyy'
		});
			
	var Accordion0 =function(el0,multiple0){
	this.el0 = el0 || {};
	this.multiple0 = multiple0 || false;

	// Variables privadas
	var links0 = this.el0.find('.link0');
	// Evento
	links0.on('click', {el0: this.el0, multiple0: this.multiple0}, this.dropdown0)
	
	//
}
Accordion0.prototype.dropdown0 = function(e) {
	var $el0 = e.data.el0,
		$this = $(this),
		$next = $this.next();

	$next.slideToggle();
	$this.parent().toggleClass('open');

	if (!e.data.multiple0) {
		$el0.find('.accordion').not($next).slideUp().parent().removeClass('open');
	};
	
	
}	
	var accordion0 = new Accordion0($('.accordion0'), false);	
	$(".linkf").click(function(){
		alert("hhahhmwosh");
	});
	/*$(".submenu").click(function(){
		//$(this).hide();
		alert("jjjjjj");
	});*/
// ----------------------------------------------------------------------//
	/*var Accordion = function(el, multiple) {
		this.el = el || {};
		this.multiple = multiple || false;

		// Variables privadas
		var links = this.el.find('.link');
		// Evento
		links.on('click', {el: this.el, multiple: this.multiple}, this.dropdown)
	}

	Accordion.prototype.dropdown = function(e) {
		var $el = e.data.el;
			$this = $(this),
			$next = $this.next();

		$next.slideToggle();
		$this.parent().toggleClass('open');

		if (!e.data.multiple) {
			$el.find('.submenu').not($next).slideUp().parent().removeClass('open');
		};
	}	
	

	//setTimeout(function(){alert("111111");},5000);
	alert("2");
	var accordion = new Accordion($('#accordion'), false);*/

	//
	// -----------------------------------------------------------------//
	
	// -----------------------------------------------------------------//
	$("#inlineRadio1").click(function(){
		 $("#sel").hide();
		 $("#inp").show();
		 $("#mpt").show();
	});
	$("#inlineRadio2").click(function(){
		 $("#inp").hide();
		 $("#mpt").hide();
		 $("#sel").show();
	});
}
	/*
	 * $("#SVGMapContent").click(function(){ alert("this is svgmap"); });
	 */
	/*
	 * $("riverOpt").blind("click",function(){
	 *  })
	 */
	
/*	$("#layer>.checkbox").each(function(){$(this).click(function(e){
		alert("Qianhaoran dou B");
		$("input[name='box'][checked]").each(function(){
		    if (false == $(this).attr("checked")) {
		          var mapOpt = $(this).attr("id");
		          switch(mapOpt){
		          case "riverOpt": ;
		          break;
		          case "roadOpt": ;
		          break;
		          case "buildingOpt": $("#building").find("*").css("visibility","hidden");
		          break;
		          case "plantingOpt": ;
		          break;
		          }
		          }
		    });
	});
	});*/
	//$("#riverOpt").attr("checked")=true;

