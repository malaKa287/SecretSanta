

$(document).ready(function(){
    //the click function does not seem to be working in the example, so I have replaced it with this.
    $(document).on('click', '.icon-delete', function(){
        $(this).parent().remove();
    });
    //Keep a single clone of the original
    var clonedField = $('#emailInput').clone(),
    main = $('#dynamic_ingredients');

    // Add in the delete <a>
    $('<a>', {
        text: 'Remove',
        class: 'icon-delete',
        href: '#'
    }).appendTo(clonedField);

    // Clone the cloned original and append it back to the list
    $('#add-ingredient').click(function() {
        main.append(clonedField.clone());
        return false;
    });


/////////
// jQuery
function replaceItems (html) {
    // Replace the <fieldset id="items"> with a new one returned by server.
    $('#items').replaceWith($(html));
}

$('button[name="addItem"]').click(function (event) {
    event.preventDefault();
    var data = $('form').serialize();
    // Add parameter "addItem" to POSTed form data. Button's name and value is
    // POSTed only when clicked. Since "event.preventDefault();" prevents from
    // actual clicking the button, following line will add parameter to form
    // data.
    data += 'addItem';
    $.post('/santa', data, replaceItems);
});

$('button[name="removeItem"]').click(function (event) {
    event.preventDefault();
    var data = $('form').serialize();
    // Add parameter and index of item that is going to be removed.
    data += 'removeItem=' + $(this).val();
    $.post('/order', data, replaceItems);
});

});
