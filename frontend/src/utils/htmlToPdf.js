// 导出页面为PDF格式

import jsPDF from 'jspdf';
import html2canvas from 'html2canvas';

const A4_WIDTH = 592.28;
const A4_HEIGHT = 841.89;



function splitPage($dom) {
  debugger
  const pageOffsetTop = $dom.offsetTop
  const pageOffsetWidth = $dom.offsetWidth
  const pageOffsetHeight = $dom.offsetHeight
  const $unitElements = $dom.querySelectorAll('.minimum-unit')

  const peerPageHeight = pageOffsetWidth / A4_WIDTH * A4_HEIGHT // 获取缩放后的一页页面高度
  const pages = [
    [{
      top: 0, // 起点初始化
      offsetTop: 0
    }]
  ]

  // 遍历最小单元格
  // 获取单元格底部距离顶部的高度 top，以及 offsetTop
  // 根据 top 值，算出该单元格的页码，放入数组 pages
  // let indexPage= 1 ;
  $unitElements.forEach(($element, index) => {
    const offsetTop = $element.offsetTop - pageOffsetTop
    const top = offsetTop + $element.offsetHeight
    const pageIndex = index + 1

    // 新的一页
    if (typeof pages[pageIndex] === 'undefined') {
      pages[pageIndex] = []
    }

    pages[pageIndex].push({
      top,
      offsetTop
    })
    console.info("pageIndex:" + pageIndex + ",top:" + top + ",offsetTop:" + offsetTop);
    //indexPage += 1
  })

  console.log(pages)

  return pages
}

function exportPDF($dom, filename) {
  // 滚动到页面顶部，避免导出不全
  document.documentElement.scrollTop = 0

  html2canvas($dom, {
    allowTaint: true,
    scale: 2
  }).then((canvas) => {
    const pdf = new jsPDF('', 'pt', 'a4')
    const contentWidth = canvas.width
    const contentHeight = canvas.height
    const pageData = canvas.toDataURL('image/jpeg', 1.0)
    const pageHeight = contentWidth / A4_WIDTH * A4_HEIGHT // 内容缩放后的高度
    const pages = splitPage($dom)

    pages.forEach((page, index) => {
      const {
        offsetTop
      } = page[0]
      const {
        top
      } = page[page.length - 1]

      if (index > 0) {
        pdf.addPage()
      }
      console.info("生成:,top:" + top + ",offsetTop:" + offsetTop);
      pdf.addImage(pageData, 'JPEG', 0, -1 * offsetTop, A4_WIDTH, top)
      // pdf.addImage(pageData, 'JPEG', 0, -1 * offsetTop, A4_WIDTH, A4_HEIGHT)

    })

    pdf.save(`${filename}.pdf`)
  })
}

export default {
  install(Vue, options) {
    Vue.prototype.$getPdf = exportPDF3
  }
}

// 这是生成一页PDF 所有内容生成一页  如果打印的话  多页不可用
function exportPDF2(dom, filename) {
  const scale = 2

  // 滚动到顶部，避免打印不全
  document.documentElement.scrollTop = 0

  html2canvas(dom, {
    allowTaint: true, // Whether to allow cross-origin images to taint the canvas
    scale // The scale to use for rendering. Defaults to the browsers device pixel ratio.
  }).then((canvas) => {
    const contentWidth = canvas.width / scale
    const contentHeight = canvas.height / scale
    const pdf = new jsPDF('', 'pt', [contentWidth, contentHeight])
    const pageData = canvas.toDataURL('image/jpeg', 1.0)

    pdf.addImage(pageData, 'JPEG', 0, 0, contentWidth, contentHeight)
    pdf.save(`${filename}.pdf`)
  })
}

function exportPDF3(dom, filename) {
  const scale = 2

  html2canvas(dom, {
    allowTaint: true, // Whether to allow cross-origin images to taint the canvas
    scale // The scale to use for rendering. Defaults to the browsers device pixel ratio.
  }).then((canvas) => {
    var contentWidth = canvas.width;
    var contentHeight = canvas.height;
    //一页pdf显示html页面生成的canvas高度;
    var pageHeight = contentWidth / 592.28 * 841.89;
    //未生成pdf的html页面高度
    var leftHeight = contentHeight;
    //页面偏移
    var position = 0;
    //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
    var imgWidth = 595.28;
    var imgHeight = 592.28 / contentWidth * contentHeight;
    var pageData = canvas.toDataURL('image/jpeg', 1.0);
    //注①
    var pdf = new jsPDF('', 'pt', 'a4');
    //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
    //当内容未超过pdf一页显示的范围，无需分页
    if (leftHeight < pageHeight) {
      pdf.addImage(pageData, 'JPEG', 0, 0, imgWidth, imgHeight);
    } else {
      while (leftHeight > 0) {
        //arg3-->距离左边距;arg4-->距离上边距;arg5-->宽度;arg6-->高度
        pdf.addImage(pageData, 'JPEG', 0, position, imgWidth, imgHeight)
        leftHeight -= pageHeight;
        position -= 841.89;
        //避免添加空白页
        if (leftHeight > 0) {
          //注②
          pdf.addPage();
        }
      }
    }
    pdf.save(`${filename}.pdf`);
  })
}
