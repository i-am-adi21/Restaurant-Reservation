from flask import *

from public import public
from admin import admin
from restaurant import restaurant
from api import api

app.secret_key="abc"

app=Flask(__name__)
app.secret_key='sdccgfs'
app.register_blueprint(public)
app.register_blueprint(admin,url_prefix='/admin')
app.register_blueprint(restaurant,url_prefix='/restaurant')
app.register_blueprint(api,url_prefix='/api')

app.run(debug=True,port=5072,host="192.168.43.141")
