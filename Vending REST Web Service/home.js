$(document).ready(function() {
    getAllItems();
  
    $('#addDollar').on('click', function() {
      addMoneyToInput(1);
    });
  
    $('#addQuater').on('click', function() {
      addMoneyToInput(2);
    });
  
    $('#addDime').on('click', function() {
      addMoneyToInput(3);
    });
  
    $('#addNickle').on('click', function() {
      addMoneyToInput(4);
    });
  
    $('#clear').on('click', function() {
      $('#moneyEntered').val('');
      $('#messagesInput').val('');
      $('#item').val('');
      $('#changeReturn').val('');
      $('#vendingItemsContainer').empty();
      getAllItems();
    });
  
    $('#makePurchase').on('click', function() {
      var isValidated = fieldsValidation();
      if (isValidated) {
        purchaseItem();
      }
    });
  
  });
  
  function addMoneyToInput(number) {
    var moneyEntered = $('#moneyEntered').val();
    if (moneyEntered == '') {
      moneyEntered = toPennies(0);
    } else {
      moneyEntered = toPennies(moneyEntered);
    }
  
    if (number == 1) {
      moneyEntered += 100;
    }
    if (number == 2) {
      moneyEntered += 25;
    }
    if (number == 3) {
      moneyEntered += 10;
    }
    if (number == 4) {
      moneyEntered += 5;
    }
  
    $('#moneyEntered').val(toBills(moneyEntered));
  }
  
  function fieldsValidation() {
  
    $('#input1ErrorMessage').empty();
    $('#input2ErrorMessage').empty();
  
    if ($('#moneyEntered').val() === '') {
      displayErrorMsg('#input1ErrorMessage', 'Fields REQUIRED in order to make a purchase.');
      return false;
    } else if ($('#item').val() === '') {
      displayErrorMsg('#input2ErrorMessage', 'Fields REQUIRED in order to make a purchase.');
      return false;
    } else {
      return true;
    }
  }
  
  function toPennies(number) {
    return number * 100;
  }
  
  function toBills(number) {
    return number / 100;
  }
  
  function purchaseItem() {
    $('#errorMessages').empty();
  
    var string_url = 'http://localhost:8080/money/' + $('#moneyEntered').val() + '/item/' + $('#item').val();
  
    $.ajax({
      type: 'GET',
      url: string_url,
      success: function(data, status) {
  
        $('#messagesInput').val('Thank You!!!');
        var row = '';
        if (data.quarters > 0) {
          row += data.quarters + ' Quater/s '
        }
        if (data.dimes > 0) {
          row += data.dimes + ' Dime/s '
        }
        if (data.nickels > 0) {
          row += data.nickels + ' Nickle/s '
        }
        if (data.pennies > 0) {
          row += data.pennies + ' Penny/ies '
        }
        $('#changeReturn').val(row);
        $('#vendingItemsContainer').empty();
        getAllItems();
      },
      error: function(data) {
        var errors = $.parseJSON(data.responseText);
        $.each(errors, function(key, value) {
          $('#messagesInput').val(value);
        });
      }
    });
  }
  
  function getAllItems() {
  
    var vendingItemsContainer = $('#vendingItemsContainer');
    vendingItemsContainer.attr({
      style: 'display:flex; flex-wrap:wrap;'
    });
  
    $.ajax({
      type: 'GET',
      url: 'http://localhost:8080/items',
      success: function(data, status) {
        $.each(data, function(index, item) {
          var id = item.id;
          var name = item.name;
          var price = item.price;
          var quantity = item.quantity;
  
          var row = '<div class="card m-2" style="width:12rem" onclick="selectItem(' + id + ')">';
          row += '<div class="card-header">' + id + '</div>'
          row += '<div class="card-body">';
          row += '<h4 class="card-title" id="' + id + 'a">' + name + '</h4>';
          row += '<p class="card-text">Price: ' + price + '</p>';
          row += '<p class="card-text">Quantity Left: ' + quantity + '</p>';
          row += '</div> </div>';
          vendingItemsContainer.append(row);
        });
      },
      error: function() {
        displayErrorMsg('#errorMessages', 'Error calling web service.  Please try again later.');
      }
    });
  }






  function displayErrorMsg(divId, message) {
    $(divId).empty();
    $(divId)
      .append($('<li>')
        .attr({
          class: 'list-group-item list-group-item-danger'
        })
        .text(message));
  }
  
  function selectItem(id) {
    var itemName = document.getElementById(id + 'a');
    $('#messagesInput').val(itemName.textContent);
    $('#item').val(id);
  }