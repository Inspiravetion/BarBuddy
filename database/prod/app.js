(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);throw new Error("Cannot find module '"+o+"'")}var f=n[o]={exports:{}};t[o][0].call(f.exports,function(e){var n=t[o][1][e];return s(n?n:e)},f,f.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
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

},{"api":2}],2:[function(require,module,exports){
var API = function(){
  this.key = '?apiKey=5074a573e4b088be4c29efc4';
  this.baseUrl = 'https://api.mongolab.com/api/1';
  this.db = '/barbuddy';
}

API.prototype.verifyUser = function(username, password){
  this.getUser(username, function(users){
    if(users[0].password == password){
      alert('YAYYY logged in');
    } else {
      alert('Not logged in');
    }
  });
}

API.prototype.getUser = function(username, cb){
  this.getDocument('/users', '&q={"username":"' + username + '"}', cb)
}

API.prototype.addUser = function(username, password){
  var data = JSON.stringify({ 'username' : username, 'password' : password });
	this.addDocument('/users', data);
}

API.prototype.removeUser = function(username){
  this.removeDocument('/users', '{"username":"' + username + '"}');
}

API.prototype.getDocument = function(collection, query, cb){
  $.get(
     this.collectionUrl() + collection + this.key + query,
    function(){
      cb.apply(this, arguments);
    }
  );
}

API.prototype.addDocument = function(collection, data) {
  $.ajax({
    url: this.collectionUrl() + collection + this.key,
    data: data,
    type: "POST",
    contentType: "application/json"
  });
}

API.prototype.removeDocument = function(collection, query){
  $.ajax({
    url: this.collectionUrl() + collection + this.key + '&q=' + query,
    data: JSON.stringify([]),
    type: "PUT",
    contentType: "application/json"
  });
}

API.prototype.collectionUrl = function(){
  return this.baseUrl + '/databases' + this.db + '/collections';
}

module.exports = API;

},{}]},{},[1])