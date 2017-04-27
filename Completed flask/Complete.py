from flask import Flask,request
#from django.http import HttpResponse
import json

app = Flask(__name__)
    
@app.route('/',methods=['POST','GET'])
def hello_world():
  if request.method == 'POST':
      data = request.form
      itemlist=json.dumps(data).split("\"")
      fil=open("ordereditems.txt",'w')
      for i in range(3,len(itemlist),4):
          fil.write(itemlist[i]+"\n")
          print (itemlist[i])
      return json.dumps(data)
  else:
      with open("available_items.txt") as f:
         lines = [line.rstrip('\n') for line in f]
      return json.dumps(lines)

if __name__ == '__main__':
   app.run('0.0.0.0','8000')
   app.run(debug=True)
