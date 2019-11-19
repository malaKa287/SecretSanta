

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
});


