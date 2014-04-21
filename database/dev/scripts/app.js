var API = require('api');

var MenuView = function(){
  var inputContainer, header, body;

  inputContainer = $('<div/>', {
    class: 'input-container'
  })
  .append(this.panel())[0];

  return inputContainer;
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
  })[0];
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
  this._tableBody = $('<tbody/>')
    .append($('<tr/>')
      .append(this.td(this.textBox))
      .append(this.td(this.textBox))
      .append(this.td(this.textBox))
      .append(this.td(this.textBox))
      .append(this.td(this.addDrinkButton)));

  return this._tableBody[0];
}

MenuView.prototype.addDrink = function(){
  var inputArr, row;

  row = $('<tr/>');
  inputArr = this._tableBody.find('input');

  for(var i = 0; i < inputArr.length; i++){
    row.append(this.td({
      text: inputArr[i].value
    }));
    inputArr[i].value = '';
  }

  row.append(this.td(this.editButton, [row]));

  row.insertBefore(this._tableBody.children().last());
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

  newRow.push(this.td(this.editButton, [row]));

  row.empty()

  newRow.forEach(function(elem){
    row.append(elem);
  });
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
  }
}

MenuView.prototype.textBox = function(hint) {
  return $('<input/>', {
    type: 'text',
    value: hint || ''
  });
}

window.onload = function(){
  document.body.appendChild(new MenuView());
}
