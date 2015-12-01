/*$(function(){
 var ins_1 = document.getElementById("ins1");
 alert(ins_1);
 ins_1.addEventListener("click",alert('yaoyao'));
 
});*/



    
function showInfo1(e){ 
	 var x,y;
	 var e = e||window.event; 
	 x = e.clientX;
     y = e.clientY; 
     console.log("e" + e);
     console.log("X" + x);
     console.log("Y" + y);
     
    $("#info1").css(
    	{
    		left:x,
    		top:y,
    		display:"block"
    	}
    );
}

function hideInfo1(e){
	 $("#info1").css(
		    	{
		    		display:"none"
		    	}
		    );
}

function showInfo2(e){ 
	 var x,y;
	 var e = e||window.event; 
	 x = e.clientX;
    y = e.clientY; 
    console.log("e" + e);
    console.log("X" + x);
    console.log("Y" + y);
    
   $("#info2").css(
   	{
   		left:x,
   		top:y,
   		display:"block"
   	}
   );
}

function hideInfo2(e){
	 $("#info2").css(
		    	{
		    		display:"none"
		    	}
		    );
}

function showInfo3(e){ 
	 var x,y;
	 var e = e||window.event; 
	 x = e.clientX;
    y = e.clientY; 
    console.log("e" + e);
    console.log("X" + x);
    console.log("Y" + y);
    
   $("#info3").css(
   	{
   		left:x,
   		top:y,
   		display:"block"
   	}
   );
}

function hideInfo3(e){
	 $("#info3").css(
		    	{
		    		display:"none"
		    	}
		    );
}
//	alert('制冷与低温工程研究所');


//function showInfo2(){
//	alert('核工程实验室');
//}
//
//function showInfo3(){
//	alert('停车场');
//}