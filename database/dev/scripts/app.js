var API = require('api');
var Drink = require('schema').Drink;
var Bar = require('schema').Bar;

var MenuView = function(username){
  this.username = username;
  this.api = new API();

  this.elem = $('<div/>', {
    class: 'input-container'
  })
  .append(this.panel())[0];
}

MenuView.prototype.panel = function(){
  var panel = $('<div/>', {
    class: 'panel panel-default'
  })
  .append(this.header())
  .append(this.body())[0];

  return panel;
}

MenuView.prototype.header = function(){
  return $('<div/>', {
    class: 'panel-heading',
    text: 'Drink Menu'
  })
  .append(this.saveButton())[0];
}

MenuView.prototype.body = function(){
  var table = $('<table/>', {
    class: 'table'
  })[0];

  table.appendChild(this.tableHead());
  table.appendChild(this.tableBody());

  return table;
}

MenuView.prototype.tableHead = function(){
  var head = $('<thead/>')
    .append($('<tr/>')
      .append(this.th('Drink'))
      .append(this.th('Price'))
      .append(this.th('Alcohol Content(%)'))
      .append(this.th('Size(oz)'))
      .append(this.th()))[0];

  return head;
}

MenuView.prototype.tableBody = function(){
  return $('<tbody/>')
    .append($('<tr/>')
      .append(this.td(this.textBox))
      .append(this.td(this.textBox))
      .append(this.td(this.textBox))
      .append(this.td(this.textBox))
      .append(this.td(this.addDrinkButton)))[0];
}

MenuView.prototype.addDrink = function(){
  var inputArr, row;

  row = $('<tr/>');
  inputArr = this._tableBody().find('input');

  for(var i = 0; i < inputArr.length; i++){
    row.append(this.td({
      text: inputArr[i].value
    }));
    inputArr[i].value = '';
  }

  row.append(this.td(this.rowButtons, [row]));

  row.insertBefore(this._tableBody().children().last());
}

MenuView.prototype.deleteDrink = function(row){
  row.remove();
}

MenuView.prototype.editRow = function(row) {
  var newRow, children;

  newRow = [];
  children = row.find('td');

  for(var i = 0; i < children.length - 1; i++){
    newRow.push(this.td(this.textBox, [children[i].innerText]));
  }

  newRow.push(this.td(this.doneButton, [row]));

  row.empty()

  newRow.forEach(function(elem){
    row.append(elem);
  });
}

MenuView.prototype.doneEditing = function(row){
  var newRow, children;

  newRow = [];
  children = row.find('input');

  for(var i = 0; i < children.length; i++){
    newRow.push(this.td({
      text: children[i].value
    }));
  }

  newRow.push(this.td(this.rowButtons, [row]));

  row.empty()

  newRow.forEach(function(elem){
    row.append(elem);
  });
}

MenuView.prototype.save = function(){
  var drinks, rows;

  rows = this._tableBody().find('tr');
  drinks = [];

  for(var i = 0; i < rows.length - 1; i++){
    drinks.push(new Drink().fromRow(rows[i]))
  }

  this.api.replaceBar(new Bar(this.username, drinks));
}

MenuView.prototype.rowButtons = function(row){
  return $('<div/>', { class: 'btn-group' })
    .append(this.editButton(row))
    .append(this.deleteButton(row));
}

MenuView.prototype.saveButton = function(){
  return $('<button/>', {
    class: 'btn btn-default save-btn',
    click: function(){
      this.save();
    }.bind(this)
  })
  .append($('<div/>', {
    class: 'glyphicon glyphicon-floppy-saved'
  }));
}

MenuView.prototype.editButton = function(row){
  return $('<button/>', {
    class: 'btn btn-default',
    text: 'Edit',
    click: function(){
      this.editRow(row);
    }.bind(this)
  });
}

MenuView.prototype.deleteButton = function(row){
  return $('<button/>', {
    class: 'btn btn-default',
    text: 'Delete',
    click: function(){
      this.deleteDrink(row);
    }.bind(this)
  });
}

MenuView.prototype.addDrinkButton = function() {
  return $('<button/>', {
    class: 'btn btn-primary',
    text: 'Add Drink',
    click: function(){
      this.addDrink();
    }.bind(this)
  });
}

MenuView.prototype.doneButton = function(row) {
  return $('<button/>', {
    class: 'btn btn-primary',
    text: 'Done',
    click: function(){
      this.doneEditing(row);
    }.bind(this)
  });
}

MenuView.prototype.th = function(label) {
  return $('<th/>', { text: label || '' });
}

MenuView.prototype.td = function(child, args){
  if(!child){
    return $('<td/>');
  }

  if(typeof child == 'function'){
    return $('<td/>').append(child.apply(this, args));
  } else if(typeof child == 'object'){
    return $('<td/>', child);
  } else if(typeof child == 'string'){
    return $('<td/>', { text : child });
  }
}

MenuView.prototype.textBox = function(hint) {
  return $('<input/>', {
    type: 'text',
    value: hint || ''
  });
}

MenuView.prototype.addRowFromDrink = function(drink){
    var row = $('<tr/>');

    row.append(this.td(drink.name))
      .append(this.td(drink.price))
      .append(this.td(drink.percent_alc))
      .append(this.td(drink.size))
      .append(this.td(this.rowButtons, [row]));

    row.insertBefore(this._tableBody().children().last());
    return this;
}

MenuView.prototype.show = function(anchor){
  (anchor || document.body).appendChild(this.elem);
  return this;
}

MenuView.prototype._tableBody = function(){
  return $(this.elem).find('tbody');
}

window.onload = function(){
  var view, d;

  d = new Drink('Sex on the Beach', '$6.00', '25%', '12oz');
  view = new MenuView('charlie').show().addRowFromDrink(d);
}
