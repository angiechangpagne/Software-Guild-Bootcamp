
var Money = 0;

$(document).ready(function(){
	updateStock();
	document.getElementById('add-dollar-btn').addEventListener('click',addDollar);
	document.getElementById('add-quarter-btn').addEventListener('click',addQtr);
	document.getElementById('add-dime-btn').addEventListener('click',addDime);
	document.getElementById('add-nickel-btn').addEventListener('click',addNickel);
	document.getElementById('purchase-btn').addEventListener('click',vendItem);
});

function updateStock(){
	$('#showItems').empty();
	var foodDisplay = $('#showItems');
	$.ajax({
		type: 'GET',
		url: 'http://localhost:8080/items', 
		success: function(AllItems, status){
			$.each(AllItems, function(index, item){

				food = $('#item-template').clone();
				food.removeClass('hide');
				food.find('.item-number').text(item.id);
				food.attr('onclick', 'selectItem(' + item.id + ')');
				food.find('.item-name').text(item.name);
				food.find('.item-price').text(item.price.toFixed(2));
				food.find('.item-quantity').text('qty: ' + item.quantity);
				foodDisplay.append(food);
				
			});
		},
		error: function(){
			Message("connection error!");
		}
	});
}

function Message(str){
	$('#display-message').text(str);
}

function selectItem(id){

	$('#showItemNum').text(id);
}

function addDollar(){
	Money += 1;
	var addValue = document.getElementById('moneyId');
	addValue.innerHTML = Money.toFixed(2);
}
function addQtr(){
	Money += 0.25;
	var addValue = document.getElementById('moneyId');
	addValue.innerHTML = Money.toFixed(2);
}
function addDime(){
	Money += .10;
	var addValue = document.getElementById('moneyId');
	addValue.innerHTML = Money.toFixed(2);
}
function addNickel(){
	Money += .05;
	var addValue = document.getElementById('moneyId');
	addValue.innerHTML = Money.toFixed(2);
}


function vendItem(){
	var itemId = $('#showItemNum').text();
	$.ajax({
		type: 'GET',
		 url: 'http://localhost:8080/money/' + Money.toFixed(2) + '/item/' + itemId,
		 success: function(change, status){
		 	var chgArray = getChg(change);
		 	displaySuccess(chgArray);
		 	updateStock();
		 },
		 error: function(result){
		 	Message(result.responseJSON.message);
		 }
	});
}

function getChg(change){
	var chgArray = [];
	chgArray.push(change.quarters);
	chgArray.push(change.dimes);
	chgArray.push(change.nickels);
	chgArray.push(change.pennies);
	return chgArray;
}

function displaySuccess(chgArray){
	displayChange(chgArray);
	$('#showItemNum').text('');
	$('#display-message').text('Thanks!');
}


function displayChange(chg){
	var result = '';
	if(chg[0] > 0) result += 'Quarters: ' + chg[0];
	if(chg[1] > 0) result += 'Dimes: ' + chg[1];
	if(chg[2] > 0) result += 'Nichels: ' + chg[2];
	if(chg[3] > 0) result += 'Pennies: ' + chg[3];
	$('#display-change').text(result);
	Money = 0;
	$('#moneyId').text('');
}