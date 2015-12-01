/**
 * Created by jasperchiu on 10/3/15.
 */
$(function(){
    //$('#editorFrame').contentWindow.svgEditor.canvas.clear()
    var editingItem= window.localStorage.getItem('editing-map-name');
    document.getElementById('editorFrame').contentWindow.svgEditor.canvas.loadMapByFolderName(editingItem);
})