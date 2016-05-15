/**
 * Created by MintMint on 2016/5/8.
 */

var pickup_switch = false;
var pickup = new TCoordinatePickup(tmap,{callback:getColor});

$("#tool_getColor").click(function(){
    if(pickup_switch)
    {
        pickup.removeEvent();
       // $("#tool_getColor").css('border','  border: 2px outset buttonface;');
        $('#tool_getColor').removeClass('colorPicker');
        $('#rgbText').html('-');
        $('#svgcanvas').removeClass('svgHide');
    }
    else
    {
        pickup.addEvent();
        $("#tool_getColor").addClass('colorPicker');
        $('#rgbText').html('Please pick color');
        $('#svgcanvas').addClass('svgHide');
    }
    pickup_switch = !pickup_switch;
});
function getColor(lnglat)
{
    var lng = lnglat.getLng();
    var lat = lnglat.getLat();
    console.log(lng);
    console.log(lat);
    $.ajax({
        type: 'POST',
        url: "/api/getColor",
        data: {
            lng:lng.toString(),
            lat:lat.toString()
        },
        success: function(result){
            var color = "rgb("+result.R+","+result.G+","+result.B+")";
            console.log(color);
            var R = result.R;
            $('.Red').children('.Text').children('input').val(R);
            var G = result.G;
            $('.Green').children('.Text').children('input').val(G);
            var B = result.B;
            $('.Blue').children('.Text').children('input').val(B);
            $('#rgbText').html("r:"+result.R+" g:"+result.G+" b:"+result.B);
            alert("r:"+result.R+"g:"+result.G+"b:"+result.B);


        },
        headers: {
            "X-Auth-Token":"eyJhY2NvdW50Ijp7InVzZXJuYW1lIjoiY2xvdWRfdXNlcjEiLCJwYXNzd29yZCI6bnVsbCwiYWN0aXZlIjp0cnVlLCJyb2xlIjoiU1BFQ0lBTF9VU0VSIn0sImV4cGlyZXMiOjE0NjMyMTE1MTg5MzQsImdyYW50ZWRBdXRocyI6WyJST0xFX1NQRUNJQUxfVVNFUiJdLCJwYXNzd29yZCI6bnVsbCwiZW5hYmxlZCI6dHJ1ZSwidXNlcm5hbWUiOiJjbG91ZF91c2VyMSIsImFjY291bnROb25FeHBpcmVkIjp0cnVlLCJhY2NvdW50Tm9uTG9ja2VkIjp0cnVlLCJjcmVkZW50aWFsc05vbkV4cGlyZWQiOnRydWV9.7nUCQZ+OOpifPC1XQcUfb8oY+kAznIbBm68XmgRpN4I="
        }
    });
    $('#rgbText').html('Calculating...Please wait.');
}

$('.Cancel').click(function() {
        pickup.removeEvent();
        // $("#tool_getColor").css('border','  border: 2px outset buttonface;');
        $('#tool_getColor').removeClass('colorPicker');
        $('#rgbText').html('-');
        $('#svgcanvas').removeClass('svgHide');
    }
);
