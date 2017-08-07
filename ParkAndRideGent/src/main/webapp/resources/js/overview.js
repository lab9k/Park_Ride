//CIRCLES
$(document).ready(function() {
    $(".circle").each(function() {
        $(this).css("background-color", capacityToColorCode($(this).text()));
    });
});

//DATATABLES INIT
$(document).ready(function() {
    $('#prtable').DataTable({
        "sDom": 't',
        "searching": true,
        "paging": false,
        "info": false,
        "ordering": true,
        "language": {
            "decimal": ",",
            "thousands": ".",
            "emptyTable": "Geen P&R locaties gevonden.",
            "loadingRecords": "Bezig met laden...",
            "processing": "Bezig met laden...",
            "zeroRecords": "Geen P&R locaties gevonden die voldoen aan de zoekopdracht."
        }
    });
});

//DATATABLES SEARCH
$('#search > input').keyup(function(){
    $('#prtable').DataTable().search($(this).val()).draw() ;
});