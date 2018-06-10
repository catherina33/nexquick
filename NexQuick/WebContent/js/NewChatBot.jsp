<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=EUC-KR">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, user-scalable=no">
<style>
.simulator-background
{
    position: absolute;
    left: 0;
    right: 0;
    top: 0;
    bottom: 0;

    background-color: #f8f8f8;
}

.simulator-background .chatContents
{
    position: absolute;
    top: 56px;
    left: 0;
    right: 0;
    bottom: 70px;
    overflow: auto;
}

.simulator-header
{
    overflow: hidden;
    height: 64px;
    background: #f8f8f8;

    text-align: center;
}

.input-box
{
    background: #fff;
    padding: 4px 16px;
    border-radius:4px;
    display: flex;
    -webkit-box-orient: horizontal;
    -webkit-box-direction: normal;
    -ms-flex-direction: row;
    flex-direction: row;
    position: absolute;
    bottom: 16px;
    left: 18px;
    right: 18px;
    box-shadow: 0 1px 11px rgba(0, 0, 0, 0.1), 0 1px 11px rgba(0, 0, 0, 0.1);
    transition: all 0.3s cubic-bezier(.25,.8,.25,1);
}

.input-box:hover
{
    box-shadow: 0 14px 28px rgba(0,0,0,0.1), 0 10px 10px rgba(0,0,0,0.15);
}

.input-box input
{
    -webkit-box-flex: 1;
    -ms-flex-positive: 1;
    flex-grow: 1;
    font-size: 16px;
    line-height: 16px;
    vertical-align: middle;
    outline: 0;
    border: 0;
}

.input-box input::-webkit-input-placeholder, .input-box input::placeholder
{
    color: #ccc;
    font-size: 14px;
}

.simulator-background .answer
{
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: normal;
    position: relative;
    z-index: 2;
    display: block;
    margin: 0 10px;
    font-size: 16px;

}
.simulator-background .answer > img
{
    position: absolute;
    top: 16px;
    left: 6px;
}

.simulator-background .answer .system-text
{
    font-size: 12px;
    margin-left: 46px;
}

.simulator-background .answer span.name
{
    font-weight: 700;
    margin-right: 10px;
}

.simulator-background .answer span.time
{
    font-size: 12px;
}

.simulator-background .answer .bubble
{
    position: relative;
    margin-bottom: 8px;
}

.simulator-background .answer .output-image img
{
    max-width: 100%;

    border-radius: 6px;
    margin-top: 10px;
}

.simulator-background .answer .output-buttons a
{
    display: block;
    color: #363636;
    text-decoration: none;

    margin: 5px 0;

    outline: 0;
    background: white;
    cursor: pointer;
    font-weight: 600;
    border: 1px solid #ddd;
    border-radius: 5px;
    padding: 8px 15px;
    font-size: 13px;
    width: 100%;
    box-sizing: border-box;
}

.simulator-background .answer .output-buttons
{
    margin-top: 15px;
}

.simulator-background .answer em.tail
{
    border-top: 10px solid #ececec;
    position: absolute;
    left: 45px;
    top: 4px;
    border-left: 10px solid transparent;
}

.simulator-background .answer .speech
{
    background: #ececec;
    display: inline-block;
    border-radius: 4px;
    padding: 10px;
    font-size: 13px;
    line-height: 17px;
    white-space: normal;
    position: relative;
    margin: 4px 52px;
}

.simulator-background .answer .speech .speech-text
{
    line-height: 18px;
}



.simulator-background .question
{
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: normal;
    position: relative;
    z-index: 2;
    display: block;
    margin: 10px;
    font-size: 16px;

}

.simulator-background .question img
{
    position: absolute;
    top: 16px;
    right: 12px;
}

.simulator-background .question .system-text
{
    font-size: 12px;
    margin-right: 10px;
    text-align: right;
}

.simulator-background .question span.name
{
    font-weight: 700;
    margin-right: 10px;
}

.simulator-background .question span.time
{
    font-size: 12px;
}

.simulator-background .question .bubble
{
    position: relative;
    margin-bottom: 8px;
}

.simulator-background .question em.tail
{
    border-top: 10px solid #dfdfdf;
    position: absolute;
    right: 7px;
    top: 4px;
    border-right: 10px solid transparent;
}

.simulator-background .question em.tail+div
{
    text-align: right;
}

.simulator-background .question .speech
{
    background: #dfdfdf;
    display: inline-block;
    border-radius: 4px;
    padding: 7px 10px 0px 10px;
    font-size: 13px;
    line-height: 17px;
    white-space: normal;
    position: relative;
    margin-top: 4px;
    margin-right: 15px;
    -webkit-box-sizing: border-box;
    -moz-box-sizing: border-box;
    box-sizing: border-box;
    text-align: left;
}

.simulator-background .question .speech .speech-text
{
    padding: 0px 0px 6px 0px;
}
</style>

<script type="text/template" id="botAnswerTemplate">
    <div class="answer">
        <img src="modules/playchat/simulator/client/imgs/bot.png">
        <div class="system-text">
            <span class="name">{botName}</span>
            <span class="time">{time}</span>
        </div>
        <div class="bubble">
            <em class="tail"></em>
            <div class="speech">
                <div class="speech-text">{text}</div>
            </div>
        </div>
    </div>
</script>

<script type="text/template" id="userAnswerTemplate">
    <div class="question">
        <div class="system-text">
            <span class="time">{time}</span>
        </div>
        <div class="bubble">
            <em class="tail"></em>
            <div>
                <div class="speech">
                    <div class="speech-text">{text}</div>
                </div>
            </div>
        </div>
    </div>
</script>
</head>
<body>
<div class="simulator-background">
    <div class="simulator-header">
        <p id="simulator-bot-name" data-id="blank_user1286_1528617943506">�ؽ�����</p>
    </div>

    <div class="chatContents" id="simulatorBody"><div class="answer">
    <img src="/modules/playchat/simulator/client/imgs/bot.png">
    <div class="system-text">
        <span class="name">�ؽ�����</span>
        <span class="time">6�� 10�� �Ͽ��� ���� 8:54</span>
    </div>
    <div class="bubble">
        <em class="tail"></em>
        <div class="speech">
            <div class="speech-text">�ȳ��ϼ���, �ؽ����� �Դϴ�.</div>
        </div>
    </div>
</div></div>

    <div class="input-box">
        <input id="simulatorInput" type="text" placeholder="�޽����� �Է��ϼ���" style="padding: 10px 7px 10px 1px;">
        <button type="button" style="border: 1px solid #ddd; background-color: transparent; padding: 12px; color: #777; outline: 0;">����</button>
    </div>
</div>

<script type="text/javascript" src="./playchat-web/socket.io.js"></script>

<script>
    (function()
    {
        var Socket = function()
        {
            this.userId = new Date().getTime();
            this.botId = document.body.querySelector('#simulator-bot-name').getAttribute('data-id');
            this.botName = document.body.querySelector('#simulator-bot-name').innerText;

            this.container = document.body.querySelector('#simulatorBody');

            if(!this.botId)
            {
                return console.error('botId is not defined');
            }

            this.init();
        };

        Socket.prototype.send = function(msg)
        {
            var params = {};
            params.bot = this.botId;
            params.user = this.userId;
            params.msg = msg;

            this.socket.emit('send_msg', params);
        };

        var getCurrentTime = function(time)
        {
            var date = undefined;
            if(time)
                date = new Date(time);
            else
                date = new Date();

            var options = { weekday: 'long', month: 'long', day: 'numeric', hour: 'numeric', minute: 'numeric' };
            return date.toLocaleDateString('ko-KR', options);
        };

        Socket.prototype.receive = function(data)
        {
            var that = this;

            var output = data.output;

            var template = '<div class="answer">\n' +
                           '    <img src="/modules/playchat/simulator/client/imgs/bot.png">\n' +
                           '    <div class="system-text">\n' +
                           '        <span class="name">{botName}</span>\n' +
                           '        <span class="time">{time}</span>\n' +
                           '    </div>\n' +
                           '    <div class="bubble">\n' +
                           '        <em class="tail"></em>\n' +
                           '        <div class="speech">\n' +
                           '            <div class="speech-text">{text}</div>\n' +
                           '        </div>\n' +
                           '    </div>\n' +
                           '</div>';

            template = template.replace('{botName}', this.botName).replace('{time}', getCurrentTime()).replace('{text}', (output.text || '' ).replace(/</gi, '&lt;').replace(/>/gi, '&gt;').replace(/\n/gi, '<br>'));

            var div = document.createElement('div');
            div.innerHTML = template;

            template = div.children[0];

            if(output.image && output.image.url)
            {
                var t = document.createElement('div');
                t.className = 'output-image';
                t.innerHTML = '<img src="' + output.image.url + '" alt="' + output.image.displayname + '">';

                console.log(template.querySelector('.speech'));
                template.querySelector('.speech').appendChild(t);
            }

            if(output.buttons)
            {
                var html = '';

                for(var i=0; i<output.buttons.length; i++)
                {
                    if(output.buttons[i].url)
                    {
                        html = '<a href="' + output.buttons[i].url + '" style="color: #038eda; border: 0;" target="_blank">' + output.buttons[i].text + '</a>' + html;
                    }
                    else
                    {
                        html += '<a style="cursor: pointer;" href="#" target="_blank">' + output.buttons[i].text + '</a>';
                    }
                }

                var t = document.createElement('div');
                t.className = 'output-buttons';
                t.innerHTML = html;

                template.querySelector('.speech').appendChild(t);
            }

            this.container.appendChild(template);

            var buttons = this.container.querySelectorAll('.output-buttons a');
            for(var i=0; i<buttons.length; i++)
            {
                buttons[i].addEventListener('click', function(e)
                {
                    var output = this.innerText;
                    that.send(output);

                    e.preventDefault();
                    e.stopPropagation();
                });
            }

            this.container.scrollTop = this.container.scrollHeight;
        };

        Socket.prototype.addUserBubble = function(text)
        {
            var template = '<div class="question">\n' +
                           '    <div class="system-text">\n' +
                           '        <span class="time">{time}</span>\n' +
                           '    </div>\n' +
                           '    <div class="bubble">\n' +
                           '        <em class="tail"></em>\n' +
                           '        <div>\n' +
                           '            <div class="speech">\n' +
                           '                <div class="speech-text">{text}</div>\n' +
                           '            </div>\n' +
                           '        </div>\n' +
                           '    </div>\n' +
                           '</div>';

            template = template.replace('{time}', getCurrentTime()).replace('{text}', text);

            var div = document.createElement('div');
            div.innerHTML = template;

            template = div.children[0];

            this.container.appendChild(template);

            this.container.scrollTop = this.container.scrollHeight;
        };

        Socket.prototype.init = function()
        {
            var that = this;

            var socket = io();
            socket.on('connect', function()
            {
                that.socket = socket;
                that.send(':reset user');

                document.body.querySelector('button').addEventListener('click', function()
                {
                    var text = this.previousElementSibling.value;
                    that.addUserBubble(text);
                    this.previousElementSibling.value = '';
                    that.send(text);
                });

                document.body.querySelector('#simulatorInput').addEventListener('keydown', function(e)
                {
                    if(e.keyCode == 13)
                    {
                        var text = this.value;
                        that.addUserBubble(text);
                        this.value = '';
                        that.send(text);
                    }
                });
            });

            socket.on('send_msg', function(data)
            {
                that.receive(data);
            });

            socket.on('disconnect', function()
            {
                var div = document.createElement('div');
                div.innerText = '������ ����Ǿ����ϴ�';
                div.style.fontSize = '12px';
                div.style.color = '#aaa';
                div.style.textAlign = 'center';

                that.container.appendChild(div);
            });
        };

        new Socket();
    })();
</script>


</body></html>