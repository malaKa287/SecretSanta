

$(document).ready(function(){

    $('.nBtn, .table, .eBtn, .dBtn').on('click',function(event){
        event.preventDefault();
        var href = $(this).attr('href');
        var text = $(this).text();

        if (text == 'Edit'){
            $.get(href, function(userdata,status){
//               $('.myForm #id').val(userdata.id);
               $('.myForm #name').val(userdata.name);
               $('.myForm #email').val(userdata.email);
            });

            $('.myForm #exampleModal').modal();
        } else{
            $('.myForm #name').val('');
            $('.myForm #email').val('');
            $('.myForm #exampleModal').modal();
        }
    });

    $('.table .delBtn').on('click', function(event){
        event.preventDefault();
        var href = $(this).attr('href');
        $('#myModal #delRef').attr('href', href);
        $('#myModal').modal();
    });

});