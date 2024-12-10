const novel = {
    init: function() {
        const _this = this;
        const NOVEL_API_PREFIX = '/novel';

        const defaultSuccessFunction = function(data) {
            console.log(data, 'data');
        };

        $('#btn-search').on('click', function() {
            _this.get('/api' + NOVEL_API_PREFIX, defaultSuccessFunction);
        });
    },
    get: function(url, successFunction) {
        const data = { };

        $.ajax({
            type: 'GET',
            url: url,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function(data) {
            successFunction(data);
        }).fail(function(error) {
            console.log(JSON.stringify(error));
            console.log(error, 'error');
        })
    },
    post: function(url, successFunction) {
        const data = {

        };

        $.ajax({
            type: 'POST',
            url: url,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
        }).done(function () {
            successFunction();
        }).fail(function (error) {
            console.log(JSON.stringify(error));
            console.log(error, 'error');
        });
    }
};

novel.init();