(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);throw new Error("Cannot find module '"+o+"'")}var f=n[o]={exports:{}};t[o][0].call(f.exports,function(e){var n=t[o][1][e];return s(n?n:e)},f,f.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
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

},{"api":2,"schema":3}],2:[function(require,module,exports){
var API = function(){
  this.key = '?apiKey=5074a573e4b088be4c29efc4';
  this.baseUrl = 'https://api.mongolab.com/api/1';
  this.db = '/barbuddy';
}

//=============================================================================
//User Calls

API.prototype.verifyUser = function(user, success, failure){
  this.getUser(user, function(users){
    if(users[0].password == user.password){
      success();
    } else {
      failure();
    }
  });
}

API.prototype.getUser = function(user, cb){
  this.getDocument('/users', user, cb)
}

API.prototype.addUser = function(user){
	this.addDocument('/users', user);
}

API.prototype.removeUser = function(user){
  this.removeDocument('/users', user);
}

//=============================================================================
//Bar Calls

API.prototype.getBar = function(username, cb){
  this.getDocument('/bars', '{"username":"' + username + '"}', cb);
}

API.prototype.addBar = function(bar){
  this.addDocument('/bars', bar);
}

API.prototype.removeBar = function(bar){
  this.removeDocument('/bars', bar);
}

API.prototype.replaceBar = function(bar){
  this.replaceDocument('/bars', bar, bar);
}

//=============================================================================
//Bottom Level Calls

API.prototype.getDocument = function(collection, queryObj, cb){
  $.get(
     this.collectionUrl() + collection + this.key + '&q=' + queryObj,
    function(){
      cb.apply(this, arguments);
    }
  );
}

API.prototype.addDocument = function(collection, data) {
  $.ajax({
    url: this.collectionUrl() + collection + this.key,
    data: JSON.stringify(data),
    type: "POST",
    contentType: "application/json"
  });
}

API.prototype.removeDocument = function(collection, queryObj){
  $.ajax({
    url: this.collectionUrl() + collection + this.key + '&q=' + queryObj,
    data: JSON.stringify([]),
    type: "PUT",
    contentType: "application/json"
  });
}

API.prototype.replaceDocument = function(collection, query, data){
  $.ajax({
    url: this.collectionUrl() + collection + this.key + '&q=' + query + '&u=true',
    data: JSON.stringify(data),
    type: "PUT",
    contentType: "application/json"
  });
}

API.prototype.collectionUrl = function(){
  return this.baseUrl + '/databases' + this.db + '/collections';
}

module.exports = API;

},{}],3:[function(require,module,exports){
var Drink = function(name, price, percent_alc, size){
  this.name = name;
  this.price = price;
  this.percent_alc = percent_alc;
  this.size = size;
}

Drink.prototype.fromRow = function(row){
  var fields = $(row).find('td');

  this.name = fields[0].innerText;
  this.price = fields[1].innerText;
  this.percent_alc = fields[2].innerText;
  this.size = fields[3].innerText;

  return this;
}

var Bar = function(username, drinks){
  this.username = username;
  this.drinks = drinks;
}

Bar.prototype.toString = function(){
  return '{"username":"' + this.username + '"}';
}  

var User = function(username, password){
  this.username = username;
  this.password = password;
}

User.prototype.toString = function(){
  return '{"username":"' + this.username + '"}';
}

module.exports = {
    'Drink': Drink,
    'User': User,
    'Bar': Bar
}

},{}]},{},[1])