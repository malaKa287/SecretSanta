
function addInputLine() {
//    var node = document.createElement("input");                 // Create an <input> node
    var node = document.querySelector('#form').content.querySelector('#emailInput').cloneNode(true);                 // Create an <input> node
//    document.getElementById("emailInput").appendChild(node);     // Append it to the parent
    document.appendChild(node);     // Append it to the parent
}

/////

$('.add').on('click', add);
$('.remove').on('click', remove);

function add() {
  var new_chq_no = parseInt($('#total_chq').val()) + 1;
  var new_input = "<input type='text' id='new_" + new_chq_no + "'>";

  $('#new_chq').append(new_input);

  $('#total_chq').val(new_chq_no);
}

function remove() {
  var last_chq_no = $('#total_chq').val();

  if (last_chq_no > 1) {
    $('#new_' + last_chq_no).remove();
    $('#total_chq').val(last_chq_no - 1);
  }
}

$(document).ready(function(){
    //the click function does not seem to be working in the example, so I have replaced it with this.
    $(document).on('click', '.icon-delete', function(){
        $(this).parent().remove();
    });
    //Keep a single clone of the original
    var clonedField = $('.ingredient_field').clone(),
    main = $('#dynamic_ingredients');

    // Add in the delete <a>
    $('<a>', {
        text: 'Remove Step',
        class: 'icon-delete',
        href: '#'
    }).appendTo(clonedField);

    // Clone the cloned original and append it back to the list
    $('#add-ingredient').click(function() {
        main.append(clonedField.clone());
        return false;
    });
});


