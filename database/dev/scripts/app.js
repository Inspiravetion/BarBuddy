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
    inputArr[i].value = '';
  }

  row.append($('<td/>').append($('<button/>', {
    class: 'btn btn-default',
    text: 'Edit',
    click: function(){
      this.editRow(row);
    }.bind(this)
  })));

  row.insertBefore(this._tableBody.children().last());
}

MenuView.prototype.editRow = function(row) {
  var newRow, children;

  newRow = [];
  children = row.find('td');

  for(var i = 0; i < children.length - 1; i++){
    newRow.push($('<td/>')
      .append($('<input/>', {
        type: 'text',
        placeholder: children[i].innerText
      })));
  }

  newRow.push($('<td/>').append($('<button/>', {
    class: 'btn btn-primary',
    text: 'Done',
    click: function(){
      this.doneEditing(row);
    }.bind(this)
  })));

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
    newRow.push($('<td/>', {
      text: children[i].value || children[i].placeholder
    }));
  }

  console.log(newRow);

  newRow.push($('<td/>').append($('<button/>', {
    class: 'btn btn-default',
    text: 'Edit',
    click: function(){
      this.editRow(row);
    }.bind(this)
  })));

  row.empty()

  newRow.forEach(function(elem){
    row.append(elem);
  });
}

window.onload = function(){
  document.body.appendChild(new MenuView());
}
