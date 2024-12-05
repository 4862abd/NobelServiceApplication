var main = {
    init: function() {
        var _this = this;

        $('#btn-login').on('click', function () {
            _this.login();
        });

        $('#btn-search').on('click', function() {
            _this.search();
        });

        $('#btn-save').on('click', function () {
            _this.save();
        });

        $('#btn-update').on('click', function() {
            _this.update();
        });

        $('#btn-delete').on('click', function() {
            _this.delete();
        });
    },
    login: function() {
        const data = {
            username: $('#username').val(),
            password: $('#password').val(),
        };

        $.ajax({
            type: 'POST',
            url: '/api/authorize',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('로그인 성공!');
            window.location.href = '/';
        }).fail(function(error) {
            alert(JSON.stringify(error));
            console.log(error, 'error');
        });
    },
    search: function() {
        const data = {};
        $.ajax({
            type: 'GET',
            url: '/api/novel',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            // data: JSON.stringify(data)
        }).done(function(result) {
            console.log(result, 'result');
            // window.location.href = '/';
        }).fail(function(error) {
            console.log(JSON.stringify(error), 'error');
        });
    },
    save: function () {
        var data = {
            title: $('#title').val(),
            author: $('#author').val(),
            content: $('#content').val(),
        };

        $.ajax({
           type: 'POST',
            url: '/api/v1/posts',
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data)
        }).done(function() {
            alert('글이 등록되었습니다.');
            window.location.href = '/';
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },
    update: function() {
        var data = {
            title: $('#title').val(),
            content: $('#content').val(),
        };

        var id = $('#id').val();

        $.ajax({
            type: 'PUT',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
            data: JSON.stringify(data),
        }).done(function() {
            alert('글이 수정되었습니다.');
            window.location.href = '/';
        }).fail(function(error) {
            alert(JSON.stringify(error));
        });
    },
    delete: function() {
        var id = $('#id').val();

        $.ajax({
            type: 'DELETE',
            url: '/api/v1/posts/' + id,
            dataType: 'json',
            contentType: 'application/json; charset=utf-8',
        }).done(function() {
            alert('글이 삭제되었습니다.')
            window.location.href = '/';
        }).fail(function (error) {
            alert(JSON.stringify(error));
        });
    }
};

main.init();