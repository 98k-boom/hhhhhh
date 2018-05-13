Page({
  data: {
    hasGoods: false, // 购物车是否有物品：true：有；false：无
  },
  onLoad: function () {
    // wx.request({
    //   url: url,
    //   method: 'GET',
    //   header: {
    //     "Content-Type": "json"
    //   },
    //   success: function (res) {
    //     callBack(res.data);
    //   },
    //   fail: function (error) {
    //     console.log(error)
    //   }
    // })
  },
  gotoShop: function () {
    wx.switchTab({
      url: "../index/index"
    });
  }
})