package com.neutec.neutecdemo

import com.neutec.neutecdemo.view.screen.EventItemData
import com.neutec.neutecdemo.websocket.Notification

object TestData {
    fun getNoticePageImportantListTestData(): MutableList<Notification> {
        val importantFakeList = mutableListOf<Notification>()
        importantFakeList.add(
            Notification(
                id = 1,
                iconUrl = "http://10.10.84.20/images/notice/icon_important.png",
                title = "今日中午餐廳備有萬聖節大餐，敬請共襄盛舉！",
                content = "",
                group = "important",
                category = "重要公告",
                isNew = 1,
                isRead = 0,
                isClose = 0,
                datetime = "2023-11-01 07:10:14"
            )
        )
        importantFakeList.add(
            Notification(
                id = 2,
                iconUrl = "http://10.10.84.20/images/notice/icon_important.png",
                title = "今天我生日！",
                content = "",
                group = "important",
                category = "公司重要活動",
                isNew = 0,
                isRead = 0,
                isClose = 0,
                datetime = "2023-11-01 07:10:13"
            )
        )
        importantFakeList.add(
            Notification(
                id = 3,
                iconUrl = "http://10.10.84.20/images/notice/icon_important.png",
                title = "今日中午餐廳備有萬聖節大餐",
                content = "",
                group = "important",
                category = "重要公告",
                isNew = 1,
                isRead = 0,
                isClose = 0,
                datetime = "2023-11-01 07:10:14"
            )
        )
        importantFakeList.add(
            Notification(
                id = 4,
                iconUrl = "http://10.10.84.20/images/notice/icon_important.png",
                title = "今日中午",
                content = "",
                group = "important",
                category = "重要公告",
                isNew = 1,
                isRead = 0,
                isClose = 0,
                datetime = "2023-11-01 07:10:14"
            )
        )

        importantFakeList.add(
            Notification(
                id = 5,
                iconUrl = "http://10.10.84.20/images/notice/icon_important.png",
                title = "等等有外客來喔！",
                content = "",
                group = "important",
                category = "外報訪客通知",
                isNew = 0,
                isRead = 1,
                isClose = 0,
                datetime = "2023-11-01 07:10:11"
            )
        )
        importantFakeList.add(
            Notification(
                id = 6,
                iconUrl = "http://10.10.84.20/images/notice/icon_important.png",
                title = "HAHAHA",
                content = "",
                group = "important",
                category = "重要公告",
                isNew = 1,
                isRead = 0,
                isClose = 0,
                datetime = "2023-11-01 07:10:14"
            )
        )
        return importantFakeList
    }

    fun getNoticePageEventListTestData(): MutableList<Notification> {
        val list = mutableListOf<Notification>()
        list.add(
            Notification(
                id = 1,
                iconUrl = "http://10.10.84.20/images/notice/icon_important.png",
                title = "路跑活動即將開始！",
                content = "",
                group = "event",
                category = "分類列表",
                isNew = 1,
                isRead = 0,
                isClose = 0,
                datetime = "2023-11-01 07:10:14"
            )
        )
        list.add(
            Notification(
                id = 2,
                iconUrl = "http://10.10.84.20/images/notice/icon_important.png",
                title = "新上架書籍，歡迎借閱。",
                content = "",
                group = "event",
                category = "最新消息",
                isNew = 1,
                isRead = 0,
                isClose = 0,
                datetime = "2023-11-01 07:10:13"
            )
        )
        return list
    }

    fun getEventPageThisMonthNewBooksData(): MutableList<EventItemData>{
        val list = mutableListOf<EventItemData>()
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/096/81/0010968175.jpg&v=65018f4bk&w=280&h=280",
                title = "深度思考的技術：最受歡迎的百萬思考課，養成不被時代淘汰的5大思考力",
                desc = "楊大輝",
                evaluate = null,
                commentsCount = null
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/74/0010977461.jpg&v=658421b9k&w=140&h=140",
                title = "納瓦爾寶典：從白手起家到財務自由， 矽谷傳奇創投家的投資哲學與人生智慧\n",
                desc = "艾瑞克．喬根森 ",
                evaluate = null,
                commentsCount = null
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/086/29/0010862947.jpg&v=5ef32b3bk&w=140&h=140",
                title = "心。人生皆為自心映照",
                desc = "稻盛和夫",
                evaluate = null,
                commentsCount = null
            )
        )
        list.add(
            EventItemData(
                url = "https://im1.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/066/62/0010666234.jpg&v=54d9ddd6k&w=140&h=140",
                title = "京瓷哲學",
                desc = "稻盛和夫",
                evaluate = null,
                commentsCount = null
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/52/0010975275.jpg&v=6565cfb9k&w=140&h=140",
                title = "男孩、鼴鼠、狐狸與馬",
                desc = "查理‧麥克斯 ",
                evaluate = null,
                commentsCount = null
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/091/47/0010914794_b_01.jpg&v=61e00d82k&w=655&h=609",
                title = "蛤蟆先生去看心理師",
                desc = "羅伯．狄保德",
                evaluate = null,
                commentsCount = null
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/092/81/0010928119.jpg&v=62b1abbbk&w=140&h=140",
                title = "逆思維",
                desc = "亞當．格蘭特",
                evaluate = null,
                commentsCount = null
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/059/48/0010594847.jpg&v=5b618bb8k&w=140&h=140",
                title = "解憂雜貨店",
                desc = "東野圭吾",
                evaluate = null,
                commentsCount = null
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/094/90/0010949035.jpg&v=63d8edc2k&w=140&h=140",
                title = "小書痴的下剋上",
                desc = "香月美夜",
                evaluate = null,
                commentsCount = null
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/15/0010971597.jpg&v=6532580ck&w=140&h=140",
                title = "重探戰後臺灣政治史：美國、國民黨政府與臺灣社會的三方角力",
                desc = "陳翠蓮",
                evaluate = null,
                commentsCount = null
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/095/09/0010950974_bc_01.jpg&v=6401daabk&w=655&h=609",
                title = "萬葉集",
                desc = "大伴家持 等",
                evaluate = null,
                commentsCount = null
            )
        )
        return list
    }

    fun getEventPageBooksData(): MutableList<EventItemData>{
        val list = mutableListOf<EventItemData>()
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/096/81/0010968175.jpg&v=65018f4bk&w=280&h=280",
                title = "深度思考的技術：最受歡迎的百萬思考課，養成不被時代淘汰的5大思考力",
                desc = "楊大輝",
                evaluate = 4.7,
                commentsCount = 99239
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/74/0010977461.jpg&v=658421b9k&w=140&h=140",
                title = "納瓦爾寶典：從白手起家到財務自由， 矽谷傳奇創投家的投資哲學與人生智慧\n",
                desc = "艾瑞克．喬根森 ",
                evaluate = 3.5,
                commentsCount = 23844
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/086/29/0010862947.jpg&v=5ef32b3bk&w=140&h=140",
                title = "心。人生皆為自心映照",
                desc = "稻盛和夫",
                evaluate = 2.6,
                commentsCount = 346
            )
        )
        list.add(
            EventItemData(
                url = "https://im1.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/066/62/0010666234.jpg&v=54d9ddd6k&w=140&h=140",
                title = "京瓷哲學",
                desc = "稻盛和夫",
                evaluate = 5.0,
                commentsCount = 1
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/52/0010975275.jpg&v=6565cfb9k&w=140&h=140",
                title = "男孩、鼴鼠、狐狸與馬",
                desc = "查理‧麥克斯 ",
                evaluate = 3.9,
                commentsCount = 23144
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/091/47/0010914794_b_01.jpg&v=61e00d82k&w=655&h=609",
                title = "蛤蟆先生去看心理師",
                desc = "羅伯．狄保德",
                evaluate = 4.3,
                commentsCount = 345
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/092/81/0010928119.jpg&v=62b1abbbk&w=140&h=140",
                title = "逆思維",
                desc = "亞當．格蘭特",
                evaluate = 4.1,
                commentsCount = 34566
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/059/48/0010594847.jpg&v=5b618bb8k&w=140&h=140",
                title = "解憂雜貨店",
                desc = "東野圭吾",
                evaluate = 4.9,
                commentsCount = 10
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/094/90/0010949035.jpg&v=63d8edc2k&w=140&h=140",
                title = "小書痴的下剋上",
                desc = "香月美夜",
                evaluate = 3.7,
                commentsCount = 1245
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/15/0010971597.jpg&v=6532580ck&w=140&h=140",
                title = "重探戰後臺灣政治史：美國、國民黨政府與臺灣社會的三方角力",
                desc = "陳翠蓮",
                evaluate = 4.0,
                commentsCount = 4567
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/095/09/0010950974_bc_01.jpg&v=6401daabk&w=655&h=609",
                title = "萬葉集",
                desc = "大伴家持 等",
                evaluate = 3.2,
                commentsCount = 13467
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/096/81/0010968175.jpg&v=65018f4bk&w=280&h=280",
                title = "深度思考的技術：最受歡迎的百萬思考課，養成不被時代淘汰的5大思考力",
                desc = "楊大輝",
                evaluate = 4.7,
                commentsCount = 99239
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/74/0010977461.jpg&v=658421b9k&w=140&h=140",
                title = "納瓦爾寶典：從白手起家到財務自由， 矽谷傳奇創投家的投資哲學與人生智慧\n",
                desc = "艾瑞克．喬根森 ",
                evaluate = 3.5,
                commentsCount = 23844
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/086/29/0010862947.jpg&v=5ef32b3bk&w=140&h=140",
                title = "心。人生皆為自心映照",
                desc = "稻盛和夫",
                evaluate = 2.6,
                commentsCount = 346
            )
        )
        list.add(
            EventItemData(
                url = "https://im1.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/066/62/0010666234.jpg&v=54d9ddd6k&w=140&h=140",
                title = "京瓷哲學",
                desc = "稻盛和夫",
                evaluate = 5.0,
                commentsCount = 1
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/52/0010975275.jpg&v=6565cfb9k&w=140&h=140",
                title = "男孩、鼴鼠、狐狸與馬",
                desc = "查理‧麥克斯 ",
                evaluate = 3.9,
                commentsCount = 23144
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/091/47/0010914794_b_01.jpg&v=61e00d82k&w=655&h=609",
                title = "蛤蟆先生去看心理師",
                desc = "羅伯．狄保德",
                evaluate = 4.3,
                commentsCount = 345
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/092/81/0010928119.jpg&v=62b1abbbk&w=140&h=140",
                title = "逆思維",
                desc = "亞當．格蘭特",
                evaluate = 4.1,
                commentsCount = 34566
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/059/48/0010594847.jpg&v=5b618bb8k&w=140&h=140",
                title = "解憂雜貨店",
                desc = "東野圭吾",
                evaluate = 4.9,
                commentsCount = 10
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/094/90/0010949035.jpg&v=63d8edc2k&w=140&h=140",
                title = "小書痴的下剋上",
                desc = "香月美夜",
                evaluate = 3.7,
                commentsCount = 1245
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/15/0010971597.jpg&v=6532580ck&w=140&h=140",
                title = "重探戰後臺灣政治史：美國、國民黨政府與臺灣社會的三方角力",
                desc = "陳翠蓮",
                evaluate = 4.0,
                commentsCount = 4567
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/095/09/0010950974_bc_01.jpg&v=6401daabk&w=655&h=609",
                title = "萬葉集",
                desc = "大伴家持 等",
                evaluate = 3.2,
                commentsCount = 13467
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/096/81/0010968175.jpg&v=65018f4bk&w=280&h=280",
                title = "深度思考的技術：最受歡迎的百萬思考課，養成不被時代淘汰的5大思考力",
                desc = "楊大輝",
                evaluate = 4.7,
                commentsCount = 99239
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/74/0010977461.jpg&v=658421b9k&w=140&h=140",
                title = "納瓦爾寶典：從白手起家到財務自由， 矽谷傳奇創投家的投資哲學與人生智慧\n",
                desc = "艾瑞克．喬根森 ",
                evaluate = 3.5,
                commentsCount = 23844
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/086/29/0010862947.jpg&v=5ef32b3bk&w=140&h=140",
                title = "心。人生皆為自心映照",
                desc = "稻盛和夫",
                evaluate = 2.6,
                commentsCount = 346
            )
        )
        list.add(
            EventItemData(
                url = "https://im1.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/066/62/0010666234.jpg&v=54d9ddd6k&w=140&h=140",
                title = "京瓷哲學",
                desc = "稻盛和夫",
                evaluate = 5.0,
                commentsCount = 1
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/52/0010975275.jpg&v=6565cfb9k&w=140&h=140",
                title = "男孩、鼴鼠、狐狸與馬",
                desc = "查理‧麥克斯 ",
                evaluate = 3.9,
                commentsCount = 23144
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/091/47/0010914794_b_01.jpg&v=61e00d82k&w=655&h=609",
                title = "蛤蟆先生去看心理師",
                desc = "羅伯．狄保德",
                evaluate = 4.3,
                commentsCount = 345
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/092/81/0010928119.jpg&v=62b1abbbk&w=140&h=140",
                title = "逆思維",
                desc = "亞當．格蘭特",
                evaluate = 4.1,
                commentsCount = 34566
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/059/48/0010594847.jpg&v=5b618bb8k&w=140&h=140",
                title = "解憂雜貨店",
                desc = "東野圭吾",
                evaluate = 4.9,
                commentsCount = 10
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/094/90/0010949035.jpg&v=63d8edc2k&w=140&h=140",
                title = "小書痴的下剋上",
                desc = "香月美夜",
                evaluate = 3.7,
                commentsCount = 1245
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/15/0010971597.jpg&v=6532580ck&w=140&h=140",
                title = "重探戰後臺灣政治史：美國、國民黨政府與臺灣社會的三方角力",
                desc = "陳翠蓮",
                evaluate = 4.0,
                commentsCount = 4567
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/095/09/0010950974_bc_01.jpg&v=6401daabk&w=655&h=609",
                title = "萬葉集",
                desc = "大伴家持 等",
                evaluate = 3.2,
                commentsCount = 13467
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/096/81/0010968175.jpg&v=65018f4bk&w=280&h=280",
                title = "深度思考的技術：最受歡迎的百萬思考課，養成不被時代淘汰的5大思考力",
                desc = "楊大輝",
                evaluate = 4.7,
                commentsCount = 99239
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/74/0010977461.jpg&v=658421b9k&w=140&h=140",
                title = "納瓦爾寶典：從白手起家到財務自由， 矽谷傳奇創投家的投資哲學與人生智慧\n",
                desc = "艾瑞克．喬根森 ",
                evaluate = 3.5,
                commentsCount = 23844
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/086/29/0010862947.jpg&v=5ef32b3bk&w=140&h=140",
                title = "心。人生皆為自心映照",
                desc = "稻盛和夫",
                evaluate = 2.6,
                commentsCount = 346
            )
        )
        list.add(
            EventItemData(
                url = "https://im1.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/066/62/0010666234.jpg&v=54d9ddd6k&w=140&h=140",
                title = "京瓷哲學",
                desc = "稻盛和夫",
                evaluate = 5.0,
                commentsCount = 1
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/52/0010975275.jpg&v=6565cfb9k&w=140&h=140",
                title = "男孩、鼴鼠、狐狸與馬",
                desc = "查理‧麥克斯 ",
                evaluate = 3.9,
                commentsCount = 23144
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/091/47/0010914794_b_01.jpg&v=61e00d82k&w=655&h=609",
                title = "蛤蟆先生去看心理師",
                desc = "羅伯．狄保德",
                evaluate = 4.3,
                commentsCount = 345
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/092/81/0010928119.jpg&v=62b1abbbk&w=140&h=140",
                title = "逆思維",
                desc = "亞當．格蘭特",
                evaluate = 4.1,
                commentsCount = 34566
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/059/48/0010594847.jpg&v=5b618bb8k&w=140&h=140",
                title = "解憂雜貨店",
                desc = "東野圭吾",
                evaluate = 4.9,
                commentsCount = 10
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/094/90/0010949035.jpg&v=63d8edc2k&w=140&h=140",
                title = "小書痴的下剋上",
                desc = "香月美夜",
                evaluate = 3.7,
                commentsCount = 1245
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/15/0010971597.jpg&v=6532580ck&w=140&h=140",
                title = "重探戰後臺灣政治史：美國、國民黨政府與臺灣社會的三方角力",
                desc = "陳翠蓮",
                evaluate = 4.0,
                commentsCount = 4567
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/095/09/0010950974_bc_01.jpg&v=6401daabk&w=655&h=609",
                title = "萬葉集",
                desc = "大伴家持 等",
                evaluate = 3.2,
                commentsCount = 13467
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/096/81/0010968175.jpg&v=65018f4bk&w=280&h=280",
                title = "深度思考的技術：最受歡迎的百萬思考課，養成不被時代淘汰的5大思考力",
                desc = "楊大輝",
                evaluate = 4.7,
                commentsCount = 99239
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/74/0010977461.jpg&v=658421b9k&w=140&h=140",
                title = "納瓦爾寶典：從白手起家到財務自由， 矽谷傳奇創投家的投資哲學與人生智慧\n",
                desc = "艾瑞克．喬根森 ",
                evaluate = 3.5,
                commentsCount = 23844
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/086/29/0010862947.jpg&v=5ef32b3bk&w=140&h=140",
                title = "心。人生皆為自心映照",
                desc = "稻盛和夫",
                evaluate = 2.6,
                commentsCount = 346
            )
        )
        list.add(
            EventItemData(
                url = "https://im1.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/066/62/0010666234.jpg&v=54d9ddd6k&w=140&h=140",
                title = "京瓷哲學",
                desc = "稻盛和夫",
                evaluate = 5.0,
                commentsCount = 1
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/52/0010975275.jpg&v=6565cfb9k&w=140&h=140",
                title = "男孩、鼴鼠、狐狸與馬",
                desc = "查理‧麥克斯 ",
                evaluate = 3.9,
                commentsCount = 23144
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/091/47/0010914794_b_01.jpg&v=61e00d82k&w=655&h=609",
                title = "蛤蟆先生去看心理師",
                desc = "羅伯．狄保德",
                evaluate = 4.3,
                commentsCount = 345
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/092/81/0010928119.jpg&v=62b1abbbk&w=140&h=140",
                title = "逆思維",
                desc = "亞當．格蘭特",
                evaluate = 4.1,
                commentsCount = 34566
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/059/48/0010594847.jpg&v=5b618bb8k&w=140&h=140",
                title = "解憂雜貨店",
                desc = "東野圭吾",
                evaluate = 4.9,
                commentsCount = 10
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/094/90/0010949035.jpg&v=63d8edc2k&w=140&h=140",
                title = "小書痴的下剋上",
                desc = "香月美夜",
                evaluate = 3.7,
                commentsCount = 1245
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/15/0010971597.jpg&v=6532580ck&w=140&h=140",
                title = "重探戰後臺灣政治史：美國、國民黨政府與臺灣社會的三方角力",
                desc = "陳翠蓮",
                evaluate = 4.0,
                commentsCount = 4567
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/095/09/0010950974_bc_01.jpg&v=6401daabk&w=655&h=609",
                title = "萬葉集",
                desc = "大伴家持 等",
                evaluate = 3.2,
                commentsCount = 13467
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/096/81/0010968175.jpg&v=65018f4bk&w=280&h=280",
                title = "深度思考的技術：最受歡迎的百萬思考課，養成不被時代淘汰的5大思考力",
                desc = "楊大輝",
                evaluate = 4.7,
                commentsCount = 99239
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/74/0010977461.jpg&v=658421b9k&w=140&h=140",
                title = "納瓦爾寶典：從白手起家到財務自由， 矽谷傳奇創投家的投資哲學與人生智慧\n",
                desc = "艾瑞克．喬根森 ",
                evaluate = 3.5,
                commentsCount = 23844
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/086/29/0010862947.jpg&v=5ef32b3bk&w=140&h=140",
                title = "心。人生皆為自心映照",
                desc = "稻盛和夫",
                evaluate = 2.6,
                commentsCount = 346
            )
        )
        list.add(
            EventItemData(
                url = "https://im1.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/066/62/0010666234.jpg&v=54d9ddd6k&w=140&h=140",
                title = "京瓷哲學",
                desc = "稻盛和夫",
                evaluate = 5.0,
                commentsCount = 1
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/52/0010975275.jpg&v=6565cfb9k&w=140&h=140",
                title = "男孩、鼴鼠、狐狸與馬",
                desc = "查理‧麥克斯 ",
                evaluate = 3.9,
                commentsCount = 23144
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/091/47/0010914794_b_01.jpg&v=61e00d82k&w=655&h=609",
                title = "蛤蟆先生去看心理師",
                desc = "羅伯．狄保德",
                evaluate = 4.3,
                commentsCount = 345
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/092/81/0010928119.jpg&v=62b1abbbk&w=140&h=140",
                title = "逆思維",
                desc = "亞當．格蘭特",
                evaluate = 4.1,
                commentsCount = 34566
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/059/48/0010594847.jpg&v=5b618bb8k&w=140&h=140",
                title = "解憂雜貨店",
                desc = "東野圭吾",
                evaluate = 4.9,
                commentsCount = 10
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/094/90/0010949035.jpg&v=63d8edc2k&w=140&h=140",
                title = "小書痴的下剋上",
                desc = "香月美夜",
                evaluate = 3.7,
                commentsCount = 1245
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/15/0010971597.jpg&v=6532580ck&w=140&h=140",
                title = "重探戰後臺灣政治史：美國、國民黨政府與臺灣社會的三方角力",
                desc = "陳翠蓮",
                evaluate = 4.0,
                commentsCount = 4567
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/095/09/0010950974_bc_01.jpg&v=6401daabk&w=655&h=609",
                title = "萬葉集",
                desc = "大伴家持 等",
                evaluate = 3.2,
                commentsCount = 13467
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/096/81/0010968175.jpg&v=65018f4bk&w=280&h=280",
                title = "深度思考的技術：最受歡迎的百萬思考課，養成不被時代淘汰的5大思考力",
                desc = "楊大輝",
                evaluate = 4.7,
                commentsCount = 99239
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/74/0010977461.jpg&v=658421b9k&w=140&h=140",
                title = "納瓦爾寶典：從白手起家到財務自由， 矽谷傳奇創投家的投資哲學與人生智慧\n",
                desc = "艾瑞克．喬根森 ",
                evaluate = 3.5,
                commentsCount = 23844
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/086/29/0010862947.jpg&v=5ef32b3bk&w=140&h=140",
                title = "心。人生皆為自心映照",
                desc = "稻盛和夫",
                evaluate = 2.6,
                commentsCount = 346
            )
        )
        list.add(
            EventItemData(
                url = "https://im1.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/066/62/0010666234.jpg&v=54d9ddd6k&w=140&h=140",
                title = "京瓷哲學",
                desc = "稻盛和夫",
                evaluate = 5.0,
                commentsCount = 1
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/52/0010975275.jpg&v=6565cfb9k&w=140&h=140",
                title = "男孩、鼴鼠、狐狸與馬",
                desc = "查理‧麥克斯 ",
                evaluate = 3.9,
                commentsCount = 23144
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/091/47/0010914794_b_01.jpg&v=61e00d82k&w=655&h=609",
                title = "蛤蟆先生去看心理師",
                desc = "羅伯．狄保德",
                evaluate = 4.3,
                commentsCount = 345
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/092/81/0010928119.jpg&v=62b1abbbk&w=140&h=140",
                title = "逆思維",
                desc = "亞當．格蘭特",
                evaluate = 4.1,
                commentsCount = 34566
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/059/48/0010594847.jpg&v=5b618bb8k&w=140&h=140",
                title = "解憂雜貨店",
                desc = "東野圭吾",
                evaluate = 4.9,
                commentsCount = 10
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/094/90/0010949035.jpg&v=63d8edc2k&w=140&h=140",
                title = "小書痴的下剋上",
                desc = "香月美夜",
                evaluate = 3.7,
                commentsCount = 1245
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/15/0010971597.jpg&v=6532580ck&w=140&h=140",
                title = "重探戰後臺灣政治史：美國、國民黨政府與臺灣社會的三方角力",
                desc = "陳翠蓮",
                evaluate = 4.0,
                commentsCount = 4567
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/095/09/0010950974_bc_01.jpg&v=6401daabk&w=655&h=609",
                title = "萬葉集",
                desc = "大伴家持 等",
                evaluate = 3.2,
                commentsCount = 13467
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/096/81/0010968175.jpg&v=65018f4bk&w=280&h=280",
                title = "深度思考的技術：最受歡迎的百萬思考課，養成不被時代淘汰的5大思考力",
                desc = "楊大輝",
                evaluate = 4.7,
                commentsCount = 99239
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/74/0010977461.jpg&v=658421b9k&w=140&h=140",
                title = "納瓦爾寶典：從白手起家到財務自由， 矽谷傳奇創投家的投資哲學與人生智慧\n",
                desc = "艾瑞克．喬根森 ",
                evaluate = 3.5,
                commentsCount = 23844
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/086/29/0010862947.jpg&v=5ef32b3bk&w=140&h=140",
                title = "心。人生皆為自心映照",
                desc = "稻盛和夫",
                evaluate = 2.6,
                commentsCount = 346
            )
        )
        list.add(
            EventItemData(
                url = "https://im1.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/066/62/0010666234.jpg&v=54d9ddd6k&w=140&h=140",
                title = "京瓷哲學",
                desc = "稻盛和夫",
                evaluate = 5.0,
                commentsCount = 1
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/52/0010975275.jpg&v=6565cfb9k&w=140&h=140",
                title = "男孩、鼴鼠、狐狸與馬",
                desc = "查理‧麥克斯 ",
                evaluate = 3.9,
                commentsCount = 23144
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/091/47/0010914794_b_01.jpg&v=61e00d82k&w=655&h=609",
                title = "蛤蟆先生去看心理師",
                desc = "羅伯．狄保德",
                evaluate = 4.3,
                commentsCount = 345
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/092/81/0010928119.jpg&v=62b1abbbk&w=140&h=140",
                title = "逆思維",
                desc = "亞當．格蘭特",
                evaluate = 4.1,
                commentsCount = 34566
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/059/48/0010594847.jpg&v=5b618bb8k&w=140&h=140",
                title = "解憂雜貨店",
                desc = "東野圭吾",
                evaluate = 4.9,
                commentsCount = 10
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/094/90/0010949035.jpg&v=63d8edc2k&w=140&h=140",
                title = "小書痴的下剋上",
                desc = "香月美夜",
                evaluate = 3.7,
                commentsCount = 1245
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/15/0010971597.jpg&v=6532580ck&w=140&h=140",
                title = "重探戰後臺灣政治史：美國、國民黨政府與臺灣社會的三方角力",
                desc = "陳翠蓮",
                evaluate = 4.0,
                commentsCount = 4567
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/095/09/0010950974_bc_01.jpg&v=6401daabk&w=655&h=609",
                title = "萬葉集",
                desc = "大伴家持 等",
                evaluate = 3.2,
                commentsCount = 13467
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/096/81/0010968175.jpg&v=65018f4bk&w=280&h=280",
                title = "深度思考的技術：最受歡迎的百萬思考課，養成不被時代淘汰的5大思考力",
                desc = "楊大輝",
                evaluate = 4.7,
                commentsCount = 99239
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/74/0010977461.jpg&v=658421b9k&w=140&h=140",
                title = "納瓦爾寶典：從白手起家到財務自由， 矽谷傳奇創投家的投資哲學與人生智慧\n",
                desc = "艾瑞克．喬根森 ",
                evaluate = 3.5,
                commentsCount = 23844
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/086/29/0010862947.jpg&v=5ef32b3bk&w=140&h=140",
                title = "心。人生皆為自心映照",
                desc = "稻盛和夫",
                evaluate = 2.6,
                commentsCount = 346
            )
        )
        list.add(
            EventItemData(
                url = "https://im1.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/066/62/0010666234.jpg&v=54d9ddd6k&w=140&h=140",
                title = "京瓷哲學",
                desc = "稻盛和夫",
                evaluate = 5.0,
                commentsCount = 1
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/52/0010975275.jpg&v=6565cfb9k&w=140&h=140",
                title = "男孩、鼴鼠、狐狸與馬",
                desc = "查理‧麥克斯 ",
                evaluate = 3.9,
                commentsCount = 23144
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/091/47/0010914794_b_01.jpg&v=61e00d82k&w=655&h=609",
                title = "蛤蟆先生去看心理師",
                desc = "羅伯．狄保德",
                evaluate = 4.3,
                commentsCount = 345
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/092/81/0010928119.jpg&v=62b1abbbk&w=140&h=140",
                title = "逆思維",
                desc = "亞當．格蘭特",
                evaluate = 4.1,
                commentsCount = 34566
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/059/48/0010594847.jpg&v=5b618bb8k&w=140&h=140",
                title = "解憂雜貨店",
                desc = "東野圭吾",
                evaluate = 4.9,
                commentsCount = 10
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/094/90/0010949035.jpg&v=63d8edc2k&w=140&h=140",
                title = "小書痴的下剋上",
                desc = "香月美夜",
                evaluate = 3.7,
                commentsCount = 1245
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/097/15/0010971597.jpg&v=6532580ck&w=140&h=140",
                title = "重探戰後臺灣政治史：美國、國民黨政府與臺灣社會的三方角力",
                desc = "陳翠蓮",
                evaluate = 4.0,
                commentsCount = 4567
            )
        )
        list.add(
            EventItemData(
                url = "https://im2.book.com.tw/image/getImage?i=https://www.books.com.tw/img/001/095/09/0010950974_bc_01.jpg&v=6401daabk&w=655&h=609",
                title = "萬葉集",
                desc = "大伴家持 等",
                evaluate = 3.2,
                commentsCount = 13467
            )
        )
        return list
    }

    fun getEventPageBooksCategoryListData(): MutableList<String>{
        val list = mutableListOf<String>()
        list.add("全部")
        list.add("暢銷榜")
        list.add("童書")
        list.add("文學小說")
        list.add("心理勵志")
        list.add("商業理財")
        list.add("漫畫/圖文書")
        list.add("醫療保健")
        list.add("生活風格")
        list.add("雜誌")
        return list
    }

    fun getEventPageBooksSortListData(): MutableList<String>{
        val list = mutableListOf<String>()
        list.add("借閱多到少")
        list.add("上架新到舊")
        list.add("評價高到低")
        list.add("留言多到少")
        return list
    }
}