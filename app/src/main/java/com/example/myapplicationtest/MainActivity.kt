package com.example.myapplicationtest

//import android.hardware.usb.UsbDevice.getDeviceId

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.app.PendingIntent
import android.bluetooth.BluetoothAdapter
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings.Secure
import android.telephony.SmsManager
import android.telephony.TelephonyManager
import android.text.Html
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import org.json.JSONObject
import java.io.IOException
import java.lang.Long.getLong


class MainActivity : AppCompatActivity() {
    private val client = OkHttpClient()

    @SuppressLint("ServiceCast")
    @RequiresApi(Build.VERSION_CODES.O)
//    var tm: TelephonyManager? = null
//    var imei: String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        class Country constructor() {
            //val countr: Array<String> = arrayOf("Australia", "rerere");
            var countrycel = mutableMapOf(
                "Australia" to "cel",
                "Bangladesh" to "сel",
                "Argentina" to "cel",
                "Bahamas" to "cel",
                "GreatBritain" to "cel"
            );
            var countrytrue = mutableMapOf(
                "Australia" to "Australia - Kanberra \nФорма правления - Федеративное государство \nРост численности населения, в год - 1,10% 246 950 чел. 110 в мире \nПлотность населения, чел./км2 - 2,92 191 в мире \nВалюта - Австралийский доллар \nЧасовые пояса - UTC+8 — UTC+10 \nS = 7 686 850 km2 \nНас. = 22 450 000, Зона в Internet .au \nМеждународные организации, в которые входит Австралия - Содружество наций, АТЭС \nВыход к морям и океанам - Коралловое, Тасманово, Арафурское, Тиморское моря \nОсновную площадь государства занимают полупустыни и пустыни, на восточном побережье расположен горный хребет — Большой Водораздельный хребет с наибольшей высотой в 2228 м (гора Костюшко). Крупнейшие реки — Дарлинг и Муррей, орошающие юго-восточную часть материка.\n" +
                        "\n" +
                        "Название «Австралия» происходит от латинского «australis», означающего «южный».",
                "Bangladesh" to "Bangladesh - Dhaka \nНародная Республика Бангладеш \nюжная часть Азии \nФорма правления - Унитарная республика \n, S = 144 000 km2 \nНас. = 164 425 000 \nРост численности населения, в год - 1,29% 2 121 083 чел. 92 в мире Плотность населения, чел./км2 - 1141,84 6 в мире \n" +
                        "Зона в Internet .bd \nОфициальные языкибенгальский, английский \nВалюта така \nЧасовой пояс - UTC-+6 \nМеждународные организации, в которые входит Бангладеш: Содружество наций \nГраничит по суше - Индия, Мьянма \nВыход к морям и океанам - Бенгальский залив \nБангладеш — государство в юго-восточной Азии, основная часть территории которого лежит на равнине, в дельте реки Ганг, между Индией и Мьянмой. На самом севере проходит горная цепь Гималаев. Страна имеет доступ к Индийскому океану (через Бенгальский залив).\n" +
                        "\n" +
                        "Государство находится в восточной части исторического региона Бенгалия, а само название «Бангладеш» дословно означает «Страна бенгальцев». В средние века Бенгалия находилась под властью правителей Индии, время от времени пытаясь бороться за свою независимость, пока в 1765 году не стала колонией Великобритании.\n" +
                        "\n" +
                        "При разделе Индии в 1947 году Бенгалия была также поделена по религиозному признаку: восточная часть, в которой население, в основном, проповедовало ислам, отошла Пакистану. 26 марта 1971 года была провозглашена независимость Бангладеша, а 16 декабря того же года, в результате вооруженной борьбы с армией Пакистана, эта независимость была признана.",
                "Argentina" to "Argentina - Buenos Aires \nАргентинская Республика \nюжная часть Южной Америки \nФорма правления - Федеративная республика \nРост численности населения, в год - 1,05% 425 450 чел. 112 в мире \nS = 2 760 990 km2 \nНас. = 40 519 000 \nПлотность населения, чел./км2 - 14,68 169 в мире \nОфициальный язык - испанский \nВалюта - песо \nЧасовой пояс - UTC-3" +
                        "\nЗона в Internet .ar \nГраничит по суше - Боливия, Парагвай, Бразилия, Уругвай, Чили \nВыход к морям и океанам - Атлантический океан \nАргентина — государство на юго-востоке Южной Америки, вытянутое с севера на юг. Всю восточную и южную часть государства занимает горный хребет Анды (наивысшая точка — г. Аконкагуа, 6960 м). По территории протекает множество крупных рек: Парана, Уругвай (по ней проходит граница с одноименным государством), Рио-Салада, Рио-Колорадо, Рио-Негро, Чубут, Рио-Чико и их многочисленные притоки.\n" +
                        "\n" +
                        "С начала XVI до начала XIX века Аргентина являлась колонией Испании и называлась Ла-Плата («plata» по-испански означает «серебро», а колонизаторы считали, что именно здесь находятся богатейшие залежи этого металла). В 1810 году произошла антииспанская «Майская революция» и в 1816 была провозглашена независимость Объединенных провинций Ла-Платы, которые спустя 10 лет были переименованы в Аргентину (от латинского argentum — серебро).",
                "Bahamas" to "Bahamas - Nassau \nСодружество Багамы \nРегион - северо-восточная часть Центральной Америки \nФорма правленияКонституционная монархия \nS,км 13 940 154 в мире \nНаселение, чел. 346 000 167 в мире \nРост численности населения, в год 0,93% 3 218 чел 121 в мире. \nПлотность населения, чел./км2 24,82 150 в мире \nОфициальный язык - английский \nВалюта - багамский доллар \nМеждународный телефонный код 1242 \nЗона в Internet .bs \nЧасовой пояс UTC-5 \nМеждународные организации, в которые входит Багамы - Содружество наций \nВыход к морям и океанам - Карибское море \nСодружество Багамских Островов — государство на одноименных островах в Карибском море (Атлантический океан), на небольшом расстоянии к северу от Кубы и к юго-востоку от Флориды. В архипелаг входит около 700 островов и коралловых рифов. Поверхность большинства из них равнинная (самая высокая точка — гора Алверниа, 63 м над уровнем моря), реки практически отсутствуют, много сообщающихся с морем соленых озер.\n" +
                        "\n" +
                        "Благодаря чистым прибрежным водам и мягкому климату Багамские острова стали признанным во всем мире зимним курортом.\n" +
                        "\n" +
                        "Острова были открыты Колумбом в 1492 и в те времена были достаточно плотно заселены индейцами, но испанцы вывозили их на Гаити в качестве рабов и вскоре архипелаг обезлюдел. Став в 1718 году английской колонией, острова пробыли в данном статусе до 10 июля 1973 года, когда им была предоставлена независимость.\n" +
                        "\n" +
                        "Название свое Багамы берут от испанских мореплавателей, назвавших их «Baja Mar», т.е. «неглубокое море» — геологическое строение архипелага таково, что глубины вокруг островов в основном небольшие и только в некоторых местах проходят глубоководные трещины.",
                "GreatBritain" to "Great Britan - London \nСоединенное Королевство Великобритании и Северной Ирландии \nсеверо-западная часть Европы \nФорма правленияКонституционная монархия \nПлощадь, км2 244 101 77 в мире \nНаселение, чел. 62 008 000 22 в мире \nРост численности населения, в год 0,28% 173 622 чел 152 в мире \nПлотность населения, чел./км2 254,03 33 в мире \nВалюта - фунт стерлингов \nМеждународный телефонный код - 44 \nЗона в Internet .uk, .gb \nЧасовой пояс - UTC+0 \nМеждународные организации, в которые входит Великобритания - НАТО (с 1949 г.), Евросоюз (с 1973 г.), Содружество наций, Большая Восьмерка \nГраничит по суше - Ирландия \nВыход к морям и океанам - Атлантический океан " +
                        "Ирландское, Северное моря \nВеликобритания — островное государство (расположено на Британских островах) на северо-западе Европы. По рельефу местности страну можно разделить на две зоны: так называемую «Высокую Британию» на севере и западе, с преобладающим гористым рельефом, и, большей частью равнинную, «Низкую Британию» на юге и востоке. Высочайшая точка страны — гора Бен-Невис, 1343 метра над уровнем моря. На территории Британских островов протекает множество рек — Темза, Северн, Трент, Мерси и др., на севере также много горных озер — Лох-Ней, Лох-Несс, Лох-Ломонд.\n" +
                        "\n" +
                        "Название «Британия» скорее всего, произошло от племен бриттов, населявших острова в античные времена. В середине первого тысячелетия нашей эры многие британские племена переместились на территорию современной Франции и область их поселения получила название «Малая Британия» или «Бретань», а историческая родина — «Великая (т.е. большая) Бретань», «Великобритания».\n" +
                        "\n" +
                        "Первыми жителями островов, о которых что-либо достоверно известно, являются кельтские племена бриттов. На рубеже нашей эры большая часть Британии стала римской провинцией, а после ухода римлян на острова переселились племена англосаксов, которые к IX веку н.э. и образовали Королевство Англия. Последующая история этого королевства, как и у многих государств того времени, была полна гражданскими волнениями, переворотами, войнами с внешними врагами. Тем не менее, государство пережило все беды. В начале XVIII века была образована Великобритания, а еще за столетие до этого начинается постепенное возникновение Британской колониальной империи, самой большой в истории человечества — в эпоху наивысшего расцвета она занимала примерно четвертую часть суши. В свое время Британии принадлежали Индия, почти вся Северная Америка, Австралия, Новая Зеландия, половина Африки, множество островов — к середине XX века (некоторые и гораздо раньше) все эти территории уже получили независимость, но некоторые из них все же формально остались под властью английской короны."
            );

            fun clickleftbutton() {
                countrycel.set("Australia", "c");
                countrycel.set("Bangladesh", "с");
                countrycel.set("Argentina", "c");
                countrycel.set("Bahamas", "c");
                countrycel.set("GreatBritain", "c");
            }

            fun setcountytoleftbtn(ctr: String) {
                countrycel.set(ctr, ""); //.put() добавить элемент
            }

            fun getcapital(cap: String): String {
                return countrycel.get(cap).toString();
            }

            fun getcountrytrue(tru: String): String {
                return countrytrue.get(tru).toString();
            }
        }

        var c: Country = Country();
        button8.setOnClickListener {
            //textView.text = "Hello Kitty!"
            //c.countr[0] = "erbium";
            c.clickleftbutton();
            //c.countrycel.set("Australia", ""); //.put() добавить элемент
            c.setcountytoleftbtn("Australia");
            //toastMe(c.getcapital("Australia"));
        }
        button9.setOnClickListener {
            if (c.getcapital("Australia") == "") {
                toastMe(c.getcountrytrue("Australia"));
                goalert(c.getcountrytrue("Australia"));
            }
        }
        //var age: Int = 23; age = 21;
        button10.setOnClickListener {
            c.clickleftbutton();
            c.setcountytoleftbtn("Bangladesh");
            //var t = c.countrycel.get("Bangladesh");
            //toastMe(t.toString());
            //toastMe(c.getcapital("Bangladesh"));
        }
        button13.setOnClickListener {
            if (c.getcapital("Bangladesh") == "") {
                toastMe(c.getcountrytrue("Bangladesh"));
                goalert(c.getcountrytrue("Bangladesh"));
            }
        }
        button12.setOnClickListener {
            c.clickleftbutton();
            c.setcountytoleftbtn("Argentina");
        }
        button11.setOnClickListener {
            if (c.getcapital("Argentina") == "") {
                toastMe(c.getcountrytrue("Argentina"));
                goalert(c.getcountrytrue("Argentina"));
            }
        }
        button.setOnClickListener {
            androidimei();
        }
        button2.setOnClickListener {

            val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            val blueToothMac = bluetoothAdapter.address;
            toastMe(blueToothMac);
//                val res1 = StringBuilder()
////            for (b in blueToothMac) {
////                //res1.append(Integer.toHexString(b & 0xFF) + ":");
////                res1.append(String.format("%02X:", b))
////                toastMe(res1.toString());
////            }
//                toastMe(blueToothMac);
//            val tm = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
//            val blueMac = tm.adapter.address;
//            toastMe(blueMac);
        }
        button3.setOnClickListener {
            val wifiManager =
                getApplicationContext().getSystemService(Context.WIFI_SERVICE) as WifiManager
            val wifiMac = wifiManager.connectionInfo.macAddress
            toastMe(wifiMac);
        }
        button4.setOnClickListener {

            val permission2 = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.SEND_SMS
            )

            if (permission2 != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.SEND_SMS),
                    1
                )
            } else {
                // SMS sent pending intent
                val SENT_ACTION = "SMS_SENT_ACTION"
                val DELIVERED_ACTION = "SMS_DELIVERED_ACTION"
                // SMS sent pending intent
                val sentIntent: PendingIntent = PendingIntent.getBroadcast(
                    this, 0,
                    Intent(SENT_ACTION), 0
                )
                // SMS delivered pending intent
                val deliveredIntent: PendingIntent = PendingIntent.getBroadcast(
                    this, 0,
                    Intent(DELIVERED_ACTION), 0
                )
                // SMS sent receiver
                registerReceiver(object : BroadcastReceiver() {
                    override fun onReceive(
                        context: Context?,
                        intent: Intent?
                    ) {
                        toastMe("SMS sent intent received.")
                    }
                }, IntentFilter(SENT_ACTION))
                // SMS delivered receiver
                registerReceiver(object : BroadcastReceiver() {
                    override fun onReceive(
                        context: Context?,
                        intent: Intent?
                    ) {
                        toastMe("SMS delivered intent received.")
                    }
                }, IntentFilter(DELIVERED_ACTION))

                // Send the SMS message

                // Send the SMS message
                val sms = SmsManager.getDefault()
                sms.sendTextMessage("89166504867", null, "HELLO WORLD", sentIntent, deliveredIntent)
            }
        }
        button5.setOnClickListener {
            run("https://httpbin.org/ip");
            //run("https://api.icndb.com/jokes/random");
        }
        button6.setOnClickListener {
            val android_id = Secure.getString(
                this.contentResolver,
                Secure.ANDROID_ID
            )
            goalert("android id:"+ android_id+"\n" +
            "название основной платы:" + Build.BOARD + "\n"+
            "имя бренда:" + Build.BRAND + "\n"+
            "название набора команд машинного кода:" + Build.CPU_ABI + "\n"+
            "название промышленного образца:" + Build.DEVICE + "\n"+
            "идентификатор сборки:" + Build.DISPLAY + "\n"+
            "строка, которая однозначно идентифицирует это устройство:" + Build.FINGERPRINT + "\n"+
            "host:" + Build.HOST + "\n"+
            "hardware:" + Build.HARDWARE + "\n"+
            "номер списка изменений:" + Build.ID + "\n"+
            "производитель устройства:" + Build.MANUFACTURER + "\n"+
            "название продукта, видимое для пользователя:" + Build.MODEL + "\n"+
            "имя продукта:" + Build.PRODUCT + "\n"+
            "теги, разделенные запятыми, описывающие сборку:" + Build.TAGS + "\n"+
            "дата производства:" + Build.TIME + "\n"+
            "тип сборки:" + Build.TYPE + "\n"+
            "user:" + Build.USER + "\n"+
            "ABIS:" + Build.SUPPORTED_ABIS + "\n"+
            "Загрузчик OS bootloader:" + Build.BOOTLOADER + "\n");
            for (x in "FLERN - EMERGENT") {
                toastMe(x.toString());
            }
        }

    }

    fun toastMe(X: String) {
        val myToast = Toast.makeText(this, X, Toast.LENGTH_SHORT) //var значит переменная
        myToast.show();
    }

    fun goalert(Y: String) {
        val aler: MyDialogFragment = MyDialogFragment(Y);
        //val myDialogFragment = MyDialogFragment()
        val manager = supportFragmentManager
        aler.show(manager, "myDialog")
    }

    fun run(url: String) {
        runOnUiThread {
            progressBar.visibility = View.VISIBLE
        }
        val request = Request.Builder()
            .url(url)
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {}

            //            override fun onResponse(call: Call, response: Response) {
//                println(response.body()?.string())
//                }
            var txt: String = "";
            override fun onResponse(call: Call?, response: Response?) {
                val json = response?.body()?.string()
                txt = (JSONObject(json).toString());
                runOnUiThread {
                    progressBar.visibility = View.GONE
                    factTv.text = Html.fromHtml(txt)
                }
            }
        })

        }




            @SuppressLint("HardwareIds")
            @RequiresApi(Build.VERSION_CODES.O)
            fun androidimei() {
                val android_id = Secure.getString(
                    this.contentResolver,
                    Secure.ANDROID_ID
                )
                //toastMe(android_id);
                //toastMe(Build.BOARD);
                //toastMe(Build.DISPLAY);
                //toastMe(Build.ID);
                //toastMe(Build.MODEL);
                //toastMe(Build.TAGS);
                //toastMe(Build.USER);
                //toastMe(Build.BRAND);
                //toastMe(Build.DEVICE);
                //toastMe(Build.HOST);
                //toastMe(Build.MANUFACTURER);
                //toastMe(Build.PRODUCT);
                //toastMe(Build.TYPE);
                val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
                val blueToothMac = bluetoothAdapter.address;
                //toastMe(blueToothMac);
                val wifiManager =
                    getApplicationContext().getSystemService(Context.WIFI_SERVICE) as WifiManager
                val wifiMac = wifiManager.connectionInfo.macAddress
                //toastMe(wifiMac);


                val permission1 = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_PHONE_STATE
                )

                if (permission1 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_PHONE_STATE),
                        1
                    )
                    //toastMe("sd");
                }
                val permission2 = ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_PHONE_NUMBERS
                )

                if (permission2 != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.READ_PHONE_NUMBERS),
                        1
                    )
                    //toastMe("sd");
                } else {
                    val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
                    val number = tm.line1Number;
                    if (number != null) {
                        Toast.makeText(
                            this, "number: " + number,
                            Toast.LENGTH_LONG
                        ).show();
                    }
                }
            }
    }

    class MyDialogFragment(msg: String) : DialogFragment() {
        val message = msg;
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            return activity?.let {
                val builder = AlertDialog.Builder(it)
                builder.setTitle("Info")
                    .setMessage(message)
                    //.setIcon(R.drawable.hungrycat)
                    .setPositiveButton("ok") { dialog, id ->
                        dialog.cancel()
                    }
                builder.create()
            } ?: throw IllegalStateException("Activity cannot be null")
        }
    }


