(function e(t,n,r){function s(o,u){if(!n[o]){if(!t[o]){var a=typeof require=="function"&&require;if(!u&&a)return a(o,!0);if(i)return i(o,!0);throw new Error("Cannot find module '"+o+"'")}var f=n[o]={exports:{}};t[o][0].call(f.exports,function(e){var n=t[o][1][e];return s(n?n:e)},f,f.exports,e,t,n,r)}return n[o].exports}var i=typeof require=="function"&&require;for(var o=0;o<r.length;o++)s(r[o]);return s})({1:[function(require,module,exports){
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
      .append($('<th/>', { text: 'Drink'}))
      .append($('<th/>', { text: 'Price'}))
      .append($('<th/>', { text: 'Alcohol Content(%)'}))
      .append($('<th/>', { text: 'Size(oz)'}))
      .append($('<th/>')))[0];

  return head;
}

MenuView.prototype.tableBody = function(){
  this._tableBody = $('<tbody/>')
    .append($('<tr/>')
      .append($('<td/>')
        .append($('<input/>', { type: 'text'})))
      .append($('<td/>')
        .append($('<input/>', { type: 'text'})))
      .append($('<td/>')
        .append($('<input/>', { type: 'text'})))
      .append($('<td/>')
        .append($('<input/>', { type: 'text'})))
      .append($('<td/>')
        .append($('<button/>', {
          class: 'btn btn-primary',
          text: 'Add Drink',
          click: function(){
            this.addDrink();
          }.bind(this)
        }))));

  return this._tableBody[0];
}

MenuView.prototype.addDrink = function(){
  var inputArr, row;

  row = $('<tr/>');
  inputArr = this._tableBody.find('input');

  for(var i = 0; i < inputArr.length; i++){
    row.append($('<td/>', {
      text: inputArr[i].value
    }));
  }

  row.append($('<td/>').append($('<button/>', {
    class: 'btn btn-default',
    text: 'Edit',
    click: function(){
      this.editRow();
    }.bind(this)
  })));

  row.insertBefore(this._tableBody.children().last());
}

MenuView.prototype.editRow = function() {

}

window.onload = function(){
  document.body.appendChild(new MenuView());
}

},{}]},{},[1])