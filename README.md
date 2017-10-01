# เกมส์ทายตัวเลข
โจทย์ให้ดึงตัวเลขมีค่าตั้งแต่ 1000-9999 ผ่าน api ของเว็บ random.org

อธิบายขั้นตอนการพัฒนาโปรแกรม

## 1. ทดลอง Request Server

เราจะทำการทดลอง Request เพื่อดูว่าจะได้ผลลัพธ์อะไรกลับมา

โดยจะใช้ Url นี้ https://api.random.org/json-rpc/1/invoke ในการ Request และต้องทำการขอ api key ก่อน โดยอ้างอิงจาก https://api.random.org/json-rpc/1/basic ใน method "generateIntegers" ซึ่งผมใช้เว็บนี้ https://gurujsonrpc.appspot.com/ ในการทดสอบแล้วใส่ค่าตามโจทย์

```    
{
    "jsonrpc": "2.0",
    "method": "generateIntegers",
    "params": {
        "apiKey": "ขอ api key ก่อน",
        "n": 1,
        "min": 1000,
        "max": 9999,
        "replacement": true
    },
    "id": 42
}
```

จากนั้นจะได้ผลลัพธ์ดังนี้

```    
{
	"jsonrpc": "2.0",
	"result": {
		"random": {
			"data": [
				2229
			],
			"completionTime": "2017-10-01 18:42:15Z"
		},
		"bitsUsed": 13,
		"bitsLeft": 246973,
		"requestsLeft": 764,
		"advisoryDelay": 110
	},
	"id": 42
}
```

ซึ่งผลลัพธ์การสุ่มเลขจะอยู่ใน data

## 2. ทำการเชื่อมต่อ API

ในที่นี้ผมได้เลือกใช้ Retrofit ในการเชื่อมต่อ โดยเราจะนำโค้ด json จากหัวข้อที่ 1 มาทำการเขียนให้เป็นแบบ Object หรือ Model ซึ่งผมจะใช้เว็บ http://www.jsonschema2pojo.org/ ในการ Generate โค้ดให้เลย 

![](https://drive.google.com/uc?id=0B9xzpIJApeOlbnJDYVdQSkNLWHM)


