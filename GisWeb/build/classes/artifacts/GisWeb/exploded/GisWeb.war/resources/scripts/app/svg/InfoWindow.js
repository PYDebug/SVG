
	$(document).ready(function(e) {

   		var myInfo = document.createElement("div");
        myInfo.setAttribute("id","infoWin");
        var myBody = document.getElementsByTagName("body")[0];
        myBody.appendChild(myInfo);

        /*$(".entity").mouseover(function(e) {
            showWin(e);

        });
        $(".entity").mouseout(function(e) {
            hideWin(e);

        });*/

    });

    function showWin(event) {

        var node = event.currentTarget.children.item(2).childNodes;

        for (var i = 0; i < node.length; i++) {
            if (node.item(i).localName == "address") {
                var addressValue = node.item(i).innerHTML;
            } 
            else if (node.item(i).localName == "phone") {
                var phoneValue = node.item(i).innerHTML;
            }
            else{}

        }

        var s 
        if (addressValue)
        {
        	s = "<p>"+addressValue+"</p>"
        }
        if (phoneValue)
        {
        	s = s+"<p>"+phoneValue+"</p>"
        }
        var X = event.clientX;
        var Y = event.clientY;
       


        $("#infoWin").html(s);
        $("#infoWin").css("position", "fixed");
        $("#infoWin").css("display", "block");
        $("#infoWin").css("border-radius","3px");
        $("#infoWin").css("background", "#eeeeee");
        $("#infoWin").css("opacity", "0.5");
        $("#infoWin").css("width", "150px");
        $("#infoWin").css("height", "75px");
        
        $("#infoWin").css({
            top: Y + "px",
            left: X + "px"
        });
        $("#infoWin > p").css("margin-left" , "10px");

    }

    function hideWin(event) {
        $("#infoWin").css("display", "none");
    }

    function hideInfo(scale){

            if(scale < 3){

                $(".entity > text").css("fill-opacity", "0");
                console.log("hide");
            }

            else{

                $(".entity > text").css("fill-opacity", "1");
                console.log("show");
            }

    }