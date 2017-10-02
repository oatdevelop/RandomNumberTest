# เกมส์ทายตัวเลข โดยใช้ Android
```  
1.โจทย์ให้สุ่มดึงตัวเลขที่มีค่าตั้งแต่ 1000-9999 ผ่าน api ของเว็บ random.org
https://api.random.org/json-rpc/1/	
  
2.ให้กรอกตัวเลขและนำไปเทียบกับตัวเลขที่ดึงมา โดย

    2.1 ถ้าไม่เท่ากัน
     
	- ให้แสดงว่าตัวเลขที่กรอกมาค่ามากกว่าหรือน้อยกว่าตัวเลขที่ดึงผ่าน api 
	
	- จากนั้นให้กรอกตัวเลขอีกครั้งหนึ่ง
	
    2.2 ถ้าเท่ากัน
     
	- ให้แสดงผลว่าทายถูกต้อง และเริ่มตาใหม่โดยให้เริ่มจากข้อ 1 อีกครั้ง
```  

อธิบายขั้นตอนการพัฒนาโปรแกรม

## 1. ทดลอง Request Server

เราจะทำการทดลอง Request เพื่อดูว่าจะได้ผลลัพธ์อะไรกลับมา

โดยจะใช้ Url นี้ https://api.random.org/json-rpc/1/invoke ในการ Request และต้องทำการขอ api key ก่อน 

โดยอ้างอิงจาก https://api.random.org/json-rpc/1/basic ใน method "generateIntegers" ซึ่งผมใช้เว็บนี้ https://gurujsonrpc.appspot.com/ ในการทดสอบแล้วใส่ค่าตามโจทย์

Request
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

Response

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

## 2. สร้างการเชื่อมต่อ HTTP 

ต้องขอ Permission ในการใช้อินเตอร์เน็ตก่อน

```
<uses-permission android:name="android.permission.INTERNET"/>
```

ต่อมาในที่นี้ผมได้เลือกใช้ Retrofit ในการติดต่อกับ Server โดยต้องทำการ Add dependencies ใน gradle ก่อนเพื่อใช้งาน

```
    compile 'com.squareup.retrofit2:retrofit:2.3.0' //เพื่อใช้งาน retrofit
    compile 'com.squareup.retrofit2:converter-gson:2.3.0' //เพื่อแปลง Json ให้เป็น Model (POJO)
```

ซึ่งเราจะต้องนำโค้ด json จากหัวข้อที่ 1 มาทำการเขียนให้เป็นแบบ Object หรือ Model(POJO) ซึ่งผมจะใช้เว็บ http://www.jsonschema2pojo.org/ ในการ Generate โค้ดให้เลย โดยทำทั้งส่วนของ Request และ Response แสดงตัวอย่างการ Generate

POJO
![Image](https://drive.google.com/uc?id=0B9xzpIJApeOlbnJDYVdQSkNLWHM)

ในที่นี้ผมสร้าง Model ในชื่อ NumberResponse กับ NumberRequest
จากนั้นเราจะทำการสร้าง Interface ในการระบุ url ของ API โดยจะใช้ POST เนื่องจากต้องส่งข้อมูลเข้าไปด้วย แสดงได้ดังนี้

BASE_URL = https://api.random.org/json-rpc/1/

```
    @POST("invoke")
    Call<NumberResponse> createModelNumber(@Body NumberRequest numberRequest);

```

## 3. สร้างการเชื่อมต่อเพื่อรับ Response จาก Server

เราต้องทำการ set ค่าเพื่อทำการ Request ดังนี้

```
        NumberRequest.Params params = new NumberRequest.Params();
        params.setApiKey(API_KEY);
        params.setN(1);
        params.setMin(1000);
        params.setMax(9999);
        params.setReplacement(true);

        NumberRequest numberRequest = new NumberRequest();
        numberRequest.setJsonrpc("2.0");
        numberRequest.setMethod("generateIntegers");
        numberRequest.setParams(params);
        numberRequest.setId(42);

	
	initProgram(numberRequest);
```

ต่อมาทำการสร้าง retrofit class เพื่อใช้ในการติดต่อ

```
Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(RandomNumberAPI.class);
        Call<NumberResponse> call = service.createModelNumber(numberRequest);

        
```

จากนั้นทำการ Request

```
call.enqueue(new Callback<NumberResponse>() {
            @Override
            public void onResponse(Call<NumberResponse> call, Response<NumberResponse> response) {
                numberResponse = response.body();
                dismissLoading();
                textViewOutput.setText("Choose Your Number");
            }

            @Override
            public void onFailure(Call<NumberResponse> call, Throwable t) {
                Log.d("MyFail" , "Error");
            }
        });
```

enqueue() จะมี 2 methods คือ onResponse(), onFailure()

โดยผลลัพธ์จะส่งมาที่ onResponse() และในส่วน onFailure() ถ้าทำงานไม่ผ่านจะเข้าที่ส่วนนี้

ซึ่งสามารถเรียกค่าผลลัพธ์ใน onResponse() โดยเรียกได้ดังนี้

ตัวอย่างโค้ด Json ตำแหน่งที่จะทำการดึงค่า
```
"result": { 
	"random": {
		"data": [
			2229
		]
```

การ Response ค่าที่ต้องการ

```
response.body().getResult().getRandom().getData().get(0);
```


## 4. สร้างเงื่อนไขการสุ่มตัวเลข

โดยเมื่อสามารถดึงค่าตัวเลขสุ่มจาก API ได้แล้ว จากนั้นทำการกำหนดเงื่อนไขโดยเปรียบเทียบดังนี้
    
ถ้าไม่เท่ากัน
     	
- ให้แสดงว่าตัวเลขที่กรอกมาค่ามากกว่าหรือน้อยกว่าตัวเลขที่ดึงผ่าน api 

- จากนั้นให้กรอกตัวเลขใหม่
    
ถ้าเท่ากัน

- ให้แสดงผลว่าทายถูกต้อง และเริ่มตาใหม่โดย Request สุ่มตัวเลขใหม่จาก API และเริ่มการทายใหม่
	
แสดงโค้ดการเปรียบเทียบได้ดังนี้	
```
public class RandomNumber {

        public String CompareRandomNumber(int myNumber, int randomNumber){
            if(myNumber < randomNumber){
                return "Your number is " + myNumber + " less than a random number";
            }else if(myNumber > randomNumber){
                return "Your number is " + myNumber + " greater than a random number";
            }else {
                return "Congratulations";
            }
        }
}
```

## ภาพตัวอย่างเกมส์ทายตัวเลข
1.เริ่มต้นโปรแกรม

![Image](https://drive.google.com/uc?id=0B9xzpIJApeOlNGk4SUdTcDhCd3M)

2.เมื่อตัวเลขที่ผู้ใช้กำหนดน้อยกว่าผลลัพธ์

![Image](https://drive.google.com/uc?id=0B9xzpIJApeOlU2NoWXA5bUpraVk)

3.เมื่อตัวเลขที่ผู้ใช้กำหนดมากกว่าผลลัพธ์

![Image](https://drive.google.com/uc?id=0B9xzpIJApeOlZVBGQmxZQ3RDNFE)

4.เมื่อตัวเลขที่ผู้ใช้กำหนดเท่ากับผลลัพธ์

![Image](https://drive.google.com/uc?id=0B9xzpIJApeOlNGRnU1ExRVJZRGc)

จบการอธิบายแล้ว ขอบคุณครับ
