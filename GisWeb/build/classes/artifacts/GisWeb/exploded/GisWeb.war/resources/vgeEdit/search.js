function search_content(){
	//alert("hhhhh");
	var xmlhttp;
  var search_input_value = document.getElementById("tool_input").value;
  //alert(search_input_value);
	if (window.XMLHttpRequest)
  		{
  			xmlhttp = new XMLHttpRequest();
  		}
	else
  		{
  			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
  		}
  	xmlhttp.onreadystatechange=function()
  		{
  			if (xmlhttp.readyState==4 && xmlhttp.status==200)
   			{

            alert(xmlhttp.responseText);

    		}
  		}
  		xmlhttp.open("POST","layerQuery/su",false);
		  xmlhttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=UTF-8");
		  xmlhttp.send(search_input_value);

}
/*$(document).ready(function(){
  $("#tool_search").click(function(){
    var search_input_value = $("#tool_input").val();
    alert(search_input_value);
    $.ajax({
      type: 'POST',
      url: "/GisWeb_GradleEclipse/layerQuery/su",
      data:'search_input_value',
      dataType: 'xml',
      success:function(data){
        alert(data);
      }
    });
  });
});*/
