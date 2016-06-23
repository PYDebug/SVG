var pickup_switch = false;
var pickup = new TCoordinatePickup(tmap,{callback:getColor});

function toolGetColor(){
  if(pickup_switch)
  {
      pickup.removeEvent();
     // $("#tool_getColor").css('border','  border: 2px outset buttonface;');
      $('#tool_getColor').removeClass('colorPicker');
      $('#rgbText').html('如需取色，请点击Color Picker开始取色');
      $('#svgcanvas').removeClass('svgHide');
  }
  else
  {
      pickup.addEvent();
      $("#tool_getColor").addClass('colorPicker');
      var str1 = '请点击地图上某一点取色'
      $('#rgbText').html(str1);
      $('#svgcanvas').addClass('svgHide');
  }
  pickup_switch = !pickup_switch;
}

$("#tool_getColor").click(function(){
    if(pickup_switch)
    {
        pickup.removeEvent();
       // $("#tool_getColor").css('border','  border: 2px outset buttonface;');
        $('#tool_getColor').removeClass('colorPicker');
        $('#rgbText').html('如需取色，请点击Color Picker开始取色');
        $('#svgcanvas').removeClass('svgHide');
    }
    else
    {
        pickup.addEvent();
        $("#tool_getColor").addClass('colorPicker');
        var str1 = '请点击地图上某一点取色'
        $('#rgbText').html(str1);
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
            $('#rgbText').html("R = "+result.R+" G = "+result.G+" B = "+result.B + "依次填入上方表单中即可得到所取的颜色");
            alert("r:"+result.R+"g:"+result.G+"b:"+result.B);


        },
        headers: {
            "X-Auth-Token":"eyJhY2NvdW50Ijp7InVzZXJuYW1lIjoiY2xvdWRfdXNlcjEiLCJwYXNzd29yZCI6bnVsbCwiYWN0aXZlIjp0cnVlLCJyb2xlIjoiU1BFQ0lBTF9VU0VSIn0sImV4cGlyZXMiOjE0NjMyMTE1MTg5MzQsImdyYW50ZWRBdXRocyI6WyJST0xFX1NQRUNJQUxfVVNFUiJdLCJwYXNzd29yZCI6bnVsbCwiZW5hYmxlZCI6dHJ1ZSwidXNlcm5hbWUiOiJjbG91ZF91c2VyMSIsImFjY291bnROb25FeHBpcmVkIjp0cnVlLCJhY2NvdW50Tm9uTG9ja2VkIjp0cnVlLCJjcmVkZW50aWFsc05vbkV4cGlyZWQiOnRydWV9.7nUCQZ+OOpifPC1XQcUfb8oY+kAznIbBm68XmgRpN4I="
        }
    });
    // $('#rgbText').html('����ȡɫ�����Եȡ�');
    $('#rgbText').html('正在取色，请稍等。');
}

$('.Cancel').click(function() {
        pickup.removeEvent();
        // $("#tool_getColor").css('border','  border: 2px outset buttonface;');
        $('#tool_getColor').removeClass('colorPicker');
        $('#rgbText').html('����ȡɫ��������Color Picker��ʼȡɫ');
        $('#svgcanvas').removeClass('svgHide');
    }
);
