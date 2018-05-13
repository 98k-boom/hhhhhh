Page({
  data: {
    imgUrls: [
      'http://img02.tooopen.com/images/20150928/tooopen_sy_143912755726.jpg',
      'http://img06.tooopen.com/images/20160818/tooopen_sy_175866434296.jpg',
      'http://img06.tooopen.com/images/20160818/tooopen_sy_175833047715.jpg'
    ],
    indicatorDots: true,
    autoplay: true,
    interval: 5000,
    duration: 500,

    loadingMore: true,
  },
  onLoad: function () {
    
  },
  onPullDownRefresh: function () {
    wx.showNavigationBarLoading(); // 显示顶部刷新图标
    setTimeout(function(){
      wx.hideNavigationBarLoading(); // 隐藏导航栏加载框
      wx.stopPullDownRefresh();
    }, 1500);
  },
  onReachBottom: function () {
    // wx.showLoading({ // 显示加载图标
    //   title: '正在加载更多...',
    // });
    // setTimeout(function(){
    //   wx.hideLoading(); // 隐藏加载框
    // }, 2000);

    wx.showNavigationBarLoading(); // 显示顶部刷新图标
    setTimeout(() => {
      wx.hideNavigationBarLoading(); // 隐藏导航栏加载框
      this.setData({
        loadingMore: false
      })
    }, 2000);
  }
})
