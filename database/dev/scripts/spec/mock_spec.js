var color = require('colors');

function throw_mock(){
    throw 'some error';
}

describe('Testing Testing...', function(){

    beforeEach(function(){
        //some initialization
    });

    it('should pass', function(){
        var a = 1 + 1;
        expect(a).toBe(2);
    });

    it('should fail', function(){
        expect(function(){
            throw_mock()
        }).toThrow('some error');
    })

});


