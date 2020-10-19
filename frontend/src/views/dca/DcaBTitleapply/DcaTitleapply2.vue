<template>
  <a-card
    :bordered="true"
    title="华中科技大学教师岗位申报表"
    bodyStyle="height:300px;overflow:auto;"
  >
    <div
      id="printpdf"
      style="padding-left:50px;"
    >
      <template v-if="currentIndex==1 || currentVisiable">
        <div style="margin-top:60px;">
          <a-row>
            <a-col
              :span="3"
              offset="13"
            >
              <div class="nametxt">
                <p>人事编号：</p>
              </div>
            </a-col>
            <a-col :span="3">
              <div class="lefttxt">
                <p>{{customApplyFirst.rsbh}}</p>
              </div>
            </a-col>
          </a-row>
        </div>
        <div style="margin-top:120px; text-align:center;">

          <div style="font-size:32px;font-weight:bold;">华中科技大学专业技术岗位</div>
          <div style="font-size:32px;font-weight:bold;">申 报 表</div>
        </div>
        <div style="margin-top:100px; text-align:center;">
          <a-row>
            <a-col
              :span="3"
              offset="8"
            >
              <div class="nametxt">
                <p>姓&nbsp;&nbsp;&nbsp;名</p>
              </div>
            </a-col>
            <a-col :span="3">
              <div class="lefttxt">
                <p>{{customApplyFirst.name}}</p>
              </div>
            </a-col>
          </a-row>
          <a-row>
            <a-col
              :span="3"
              offset="8"
            >
              <div class="nametxt">
                <p>所&nbsp;在&nbsp;院</br>（系、所）</p>
              </div>
            </a-col>
            <a-col :span="3">
              <div class="lefttxt">
                <p>{{customApplyFirst.szyx}}</p>
              </div>
            </a-col>
          </a-row>
          <a-row>
            <a-col
              :span="3"
              offset="8"
            >
              <div class="nametxt">
                <p>现&nbsp;岗&nbsp;位</br>（职 务）</p>
              </div>
            </a-col>
            <a-col :span="3">
              <div class="lefttxt">
                <p>{{customApplyFirst.xgwzw}}</p>
              </div>
            </a-col>
          </a-row>
          <a-row>
            <a-col
              :span="3"
              offset="8"
            >
              <div class="nametxt">
                <p>拟聘岗位</br>（职 务）</p>
              </div>
            </a-col>
            <a-col :span="3">
              <div class="lefttxt">
                <p>{{customApplyFirst.npgwzw}}</p>
              </div>
            </a-col>
          </a-row>
          <a-row>
            <a-col
              :span="3"
              offset="8"
            >
              <div class="nametxt">
                <p>岗位类别</p>
              </div>
            </a-col>
            <a-col :span="3">
              <div class="lefttxt">
                <p>{{customApplyFirst.gwlb}}</p>
              </div>
            </a-col>
          </a-row>
        </div>
        <div style="margin-top:150px; text-align:center;">
          <div style="font-size:24px;font-weight:bold;">华中科技大学聘任委员会制</div>
        </div>
      </template>
      <template v-if="currentIndex==2  || currentVisiable">
        <table class="customTable">
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;text-align:center; width:120px;">姓名</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:120px;">{{customApplyFirst.name}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:80px;text-align:center;">性别</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;">{{customApplyFirst.sex}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:center;">出生年月</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;">{{customApplyFirst.birthday}}</td>
            <td
              rowspan="3"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:120px;"
            ></td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;text-align:center; ">现专业技术岗位</td>
            <td
              colspan="2"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;"
            >{{customApplyFirst.xzyjsgw}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;text-align:center;">聘任时间</td>
            <td
              colspan="2"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;"
            >{{customApplyFirst.prsj}}</td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;text-align:center; ">来校工作时间</td>
            <td
              colspan="2"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;"
            >{{customApplyFirst.lxgzsj}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px; text-align:center;">现从事专业</br>及专长</td>
            <td
              colspan="2"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;"
            >{{customApplyFirst.xcszyjzc}}</td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 80px;text-align:center; ">教师资格证编号及</br>获得时间</td>
            <td
              colspan="6"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 80px;"
            >{{customApplyFirst.jszgzbhjhdsj}}</td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 80px;text-align:center; ">担任辅导员教师班</br>主任及考核情况</td>
            <td
              colspan="6"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 80px;"
            >{{customApplyFirst.drfdyjsbzrjkhqk}}</td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 80px;text-align:center; ">社会兼职</td>
            <td
              colspan="6"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 80px;"
            >{{customApplyFirst.shjz}}</td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 80px;text-align:center; ">何时何地受</br>何奖励及处分</td>
            <td
              colspan="6"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 80px;"
            >{{customApplyFirst.hshdshjljcf}}</td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 80px;text-align:center; ">完成上一聘期</br>工作任务情况</td>
            <td
              colspan="6"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 80px;"
            >{{customApplyFirst.wcsypqgzrwqk}}</td>
          </tr>
        </table>
        <table class="customTable">
          <tr>
            <td
              colspan="4"
              style="border-left: 1px solid black;height: 40px;text-align:center;"
            >{{experciceTr}}</td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;text-align:center; width:100px;">自何年月</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:120px;text-align:center;">至何年月</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:440px;text-align:center;">在何地、何学校、和单位任何值（或学习）</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:80px;text-align:center;">证明人</td>
          </tr>
          <tr v-for="item in customApplyFirst.dcaBEducationexpericeList">
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 30px;text-align:center; padding-left:5px;">{{item.expStartTime}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 30px;text-align:center;padding-left:5px;">{{item.expEndTime}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 30px;text-align:left;padding-left:5px;">{{item.expAddress}} {{item.expSchool}} {{item.expPosition}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 30px;text-align:left;padding-left:5px;">{{item.expCertifier}}</td>
          </tr>
        </table>
      </template>
      <template v-if="currentIndex==3  || currentVisiable">
        <table class="customTable">
          <tr>
            <td
              colspan="6"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;text-align:center;"
            >
              个人思想政治及师德师风表现情况
            </td>
          </tr>
          <tr>
            <td
              colspan="6"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 280px;text-align:left;"
            >
              {{customApplyFirst.grsxzzjsdsf}}
            </td>
          </tr>
          <tr>
            <td
              colspan="6"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;text-align:center;"
            >
              申报拟聘岗位理由（限3000字以内）</br>
              （根据本人情况如实填写，包括满足申报条件情况，重点突出在教学、科研、社会服务等方面取得的主要成果）
            </td>
          </tr>
          <tr>
            <td
              colspan="6"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 280px;text-align:left;"
            >
              {{customApplyFirst.sbnpgwly}}
            </td>
          </tr>
          </table>
      </template>
      <template v-if="currentIndex==4  || currentVisiable">
        <table class="customTable">
          <tr>
            <td
              colspan="8"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 50px;text-align:center;"
            >
              近5年（任现职不满五年的按现任职以来）完成本科教学情况
            </td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;text-align:center; width:30px;">序号</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:140px;text-align:center;">课程名称</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:120px;text-align:center;">起止年月</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:150px;text-align:center;">课程类别</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:60px;text-align:center;">学生</br>人数</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:80px;text-align:center;">总学时</br>（周）数</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:100px;text-align:center;">本人承担学</br>时（周）数</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:60px;text-align:center;">教学</br>评分</td>
          </tr>
          <tr v-for="(item,index) in customApplyFirst.dcaBUndergraduateList">
            <td style="border-top: 1px solid black;border-left: 1px solid black;text-align:height: 60px;width:30px;center; ">{{index+1}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:140px;text-align:center;">{{item.courseName}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:120px;text-align:center;">{{item.ugStartDate}}-{{item.ugEndDate}}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:150px;text-align:center;">{{item.courseType}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:60px;text-align:center;">{{item.studentNumber}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:80px;text-align:center;">{{item.totalTime}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:100px;text-align:center;">{{item.personTime}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:60px;text-align:center;">{{item.teachScore}}</td>
          </tr>
        </table>
        <table class="customTable">
          <tr>
            <td
              colspan="7"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 50px;text-align:center;"
            >
              任现职以来承担的本科教学改革及建设项目
            </td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;text-align:center; width:40px;">序号</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:260px;text-align:center;">项目名称</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:center;">项目性质及</br>来源</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:center;">合同经费/</br>实到经费</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:center;">批准年月</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:center;">起止年月</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:40px;text-align:center;">本人</br>排名</td>
          </tr>
          <tr v-for="(item,index) in customApplyFirst.dcaBInnovatebuildList">
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:40px;text-align:cennter; ">{{index+1}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:260px;text-align:left;">{{item.projectName}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:left;">{{item.projectType}} {{item.projectSource}} </td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:left;">{{item.contractFund}}/{{item.realFund}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:left;">{{item.auditDate2}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:left;">{{item.startDate}}-{{item.endDate}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:40px;text-align:left;">{{item.rankNum}}</td>
          </tr>
        </table>
        <table class="customTable">
          <tr>
            <td
              colspan="6"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 50px;text-align:center;"
            >
              任现职以来本科教学工作获奖情况
            </td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;text-align:center; width:60px;">序号</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:280px;text-align:center;">获奖项目名称</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:120px;text-align:center;">奖项级别及</br>等级</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:120px;text-align:center;">授奖部门</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:80px;text-align:center;">获奖年月</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:80px;text-align:center;">本人</br>排名</td>
          </tr>
          <tr v-for="(item,index) in customApplyFirst.dcaBScientificprizeList">
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:60px;text-align:cennter; ">{{index+1}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:280px;text-align:left;">{{item.spProjectName}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:120px;text-align:left;">{{item.srProjectGrade}} {{item.srProjectLevel}} </td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:120px;text-align:left;">{{item.srPrizeDept}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:80px;text-align:left;">{{item.srPrizeDate}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:80px;text-align:left;">{{item.srPrizeRanknum}}</td>
          </tr>
        </table>
        <table class="customTable">
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;text-align:center;width:330px;">
              近5年（任现职不满五年的按现任职以来）教学工作在本单位总体评价情况
            </td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;text-align:center;width:410px;">
              <div class="lefttxt">
                <p>{{}}</p>%
              </div>
              教务处负责人签字：</br>
              （公章）
            </td>
          </tr>
        </table>
        <table class="customTable">
          <tr>
            <td
              colspan="7"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 50px;text-align:center;"
            >
              任现职以来完成研究生教学、人才培养情况
            </td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;text-align:center; width:30px;">序号</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:140px;text-align:center;">课程名称</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:120px;text-align:center;">起止年月</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:150px;text-align:center;">课程类别</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:60px;text-align:center;">学生</br>人数</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:120px;text-align:center;">总学时</br>（周）数</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:120px;text-align:center;">本人承担学</br>时（周）数</td>
          </tr>
          <tr v-for="(item,index) in customApplyFirst.dcaBTalentList">
            <td style="border-top: 1px solid black;border-left: 1px solid black;text-align:height: 60px;width:30px;center; ">{{index+1}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:140px;text-align:center;">{{item.taletName}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:120px;text-align:center;">{{item.talentStartDate}}-{{item.talentEndDate}}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:150px;text-align:center;">{{item.talentType}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:60px;text-align:center;">{{item.studentNumber}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:120px;text-align:center;">{{item.totalTime}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 40px;width:120px;text-align:center;">{{item.personTime}}</td>

          </tr>
        </table>
      </template>
      <template v-if="currentIndex==5  || currentVisiable">
        <table class="customTable">
          <tr>
            <td
              colspan="6"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 50px;text-align:center;"
            >
              任现职以来独立指导研究生情况
            </td>
          </tr>
          <tr>
            <td
              rowspan="2"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;text-align:center; width:150px;"
            >在读人数</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 30px;width:100px;text-align:center;">博士</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 30px;width:120px;text-align:center;">customApplyFirst.dcaBGraduate.doctorNumber</td>
            <td
              rowspan="2"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:150px;text-align:center;"
            >已毕业人数</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 30px;width:100px;text-align:center;">博士</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 30px;width:120px;text-align:center;">customApplyFirst.dcaBGraduate.doctorDoneNumber</td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 30px;width:100px;text-align:center;">硕士</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 30px;width:120px;text-align:center;">customApplyFirst.dcaBGraduate.graduateNumber</td>

            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 30px;width:100px;text-align:center;">硕士</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 30px;width:120px;text-align:center;">customApplyFirst.dcaBGraduate.graduateDoneNumber</td>
          </tr>
          <tr>
            <td
              colspan="2"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 30px;width:100px;text-align:center;"
            >已毕业硕士、博士论文获奖情况</td>
            <td
              colspan="4"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 30px;width:120px;text-align:center;"
            >customApplyFirst.dcaBGraduate.prizeContent</td>

          </tr>
        </table>
        <table class="customTable">
          <tr>
            <td
              colspan="6"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 50px;text-align:center;"
            >
              任现职以来发表的教学论文、出版教材等</br>
              （仅填写第一或通讯作者论文）
            </td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;text-align:center; width:30px;">序号</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:200px;text-align:center;">论文（教材）名</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:center;">期刊名或</br>出版社</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:center;">期刊号或</br>书号</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:90px;text-align:center;">发表年月</br>或出版年月</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:60px;text-align:center;">收录情况</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:60px;text-align:center;">期刊影</br>响因子</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:40px;text-align:center;">他引</br>次数</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:60px;text-align:center;">第一或</br>通讯作</br>者</td>
          </tr>
          <tr v-for="(item,index) in customApplyFirst.dcaBPaperspublishList">
            <td style="border-top: 1px solid black;border-left: 1px solid black;text-align:center; width:30px;">{{index+1}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;;width:200px;text-align:center;">{{item.paperName}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;width:100px;text-align:center;">{{item.journalName}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;width:100px;text-align:center;">{{item.journalCode}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;width:90px;text-align:center;">{{item.paperPublishdate}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;width:60px;text-align:center;">{{item.paperShoulu}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;width:60px;text-align:center;">{{item.paperCause}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;width:40px;text-align:center;">{{item.otherTimes}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;width:60px;text-align:center;">{{item.authorRank}}</td>
          </tr>
        </table>
        <table class="customTable">
          <tr>
            <td
              colspan="6"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 50px;text-align:center;"
            >
              任现职以来发表的科研论文、出版著作等</br>
              （仅填写第一或通讯作者论文，限填15篇，按重要性排序）
            </td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;text-align:center; width:30px;">序号</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:200px;text-align:center;">论文（教材）名</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:center;">期刊名或</br>出版社</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:center;">期刊号或</br>书号</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:90px;text-align:center;">发表年月</br>或出版年月</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:60px;text-align:center;">收录情况</br>影响因子</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:60px;text-align:center;">是否国际</br>一流期刊</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:40px;text-align:center;">他引</br>次数</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:60px;text-align:center;">第一或</br>通讯作</br>者</td>
          </tr>
          <tr v-for="(item,index) in customApplyFirst.dcaBSciencepublishList">
            <td style="border-top: 1px solid black;border-left: 1px solid black;text-align:center; width:30px;">{{index+1}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;;width:200px;text-align:center;">{{item.paperName}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;width:100px;text-align:center;">{{item.journalName}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;width:100px;text-align:center;">{{item.journalCode}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;width:90px;text-align:center;">{{item.paperPublishdate}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;width:60px;text-align:center;">{{item.paperShoulu}}</br>{{item.paperCause}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;width:60px;text-align:center;">{{item.isBest?'是':'否'}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;width:40px;text-align:center;">{{item.otherTimes}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;width:60px;text-align:center;">{{item.authorRank}}</td>
          </tr>
        </table>
      </template>
      <template v-if="currentIndex==6  || currentVisiable">
        <table class="customTable">
          <tr>
            <td
              colspan="7"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 50px;text-align:center;"
            >
              任现职以来承担的主要科研项目
            </td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;text-align:center; width:40px;">序号</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:260px;text-align:center;">项目名称</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:center;">项目性质及</br>来源</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:center;">合同经费/</br>实到经费</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:center;">批准年月</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:center;">起止年月</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:40px;text-align:center;">本人</br>排名</td>
          </tr>
          <tr v-for="(item,index) in customApplyFirst.dcaBSciencesearchList">
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:40px;text-align:cennter; ">{{index+1}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:260px;text-align:left;">{{item.projectName}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:left;">{{item.projectType}} {{item.projectSource}} </td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:left;">{{item.contractFund}}/{{item.realFund}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:left;">{{item.auditDate2}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:100px;text-align:left;">{{item.startDate}}-{{item.endDate}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:40px;text-align:left;">{{item.rankNum}}</td>
          </tr>
        </table>
        <table class="customTable">
          <tr>
            <td
              colspan="6"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 50px;text-align:center;"
            >
              任现职以来科研获奖情况
            </td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;text-align:center; width:60px;">序号</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:280px;text-align:center;">获奖项目名称</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:120px;text-align:center;">奖项级别及</br>等级</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:120px;text-align:center;">授奖部门</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:80px;text-align:center;">获奖年月</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:80px;text-align:center;">本人</br>排名</td>
          </tr>
          <tr v-for="(item,index) in customApplyFirst.dcaBScientificprizeList">
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:60px;text-align:cennter; ">{{index+1}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:280px;text-align:left;">{{item.spProjectName}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:120px;text-align:left;">{{item.srProjectGrade}} {{item.srProjectLevel}} </td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:120px;text-align:left;">{{item.srPrizeDept}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:80px;text-align:left;">{{item.srPrizeDate}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:80px;text-align:left;">{{item.srPrizeRanknum}}</td>
          </tr>
        </table>
      </template>
      <template v-if="currentIndex==7  || currentVisiable">
        <table class="customTable">
          <tr>
            <td
              colspan="9"
              style="border-top: 1px solid black;border-left: 1px solid black;height: 50px;text-align:center;"
            >
              任现职以来申请专利情况
            </td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;text-align:center; width:40px;">序号</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:80px;text-align:center;">专利号</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:200px;text-align:center;">专利名称</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:80px;text-align:center;">专利类别</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:80px;text-align:center;">批准年月</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:60px;text-align:center;">本人排名</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:60px;text-align:center;">是否授权</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:60px;text-align:center;">是否转让</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:80px;text-align:center;">转让收益</td>
          </tr>
          <tr v-for="(item,index) in customApplyFirst.dcaBPatentList">
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:40px;text-align:cennter; ">{{index+1}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:80px;text-align:left;">{{item.patentCode}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:200px;text-align:left;">{{item.patentName}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:80px;text-align:left;">{{item.patentType}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:80px;text-align:left;">{{item.patentDate}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:60px;text-align:left;">{{item.patentRanknum}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:60px;text-align:center;">{{item.isAuthority}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:60px;text-align:center;">{{item.isZhuanrang}}</td>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px;width:80px;text-align:left;">{{item.patentGood}}</td>
          </tr>
        </table>
        <table class="customTable">
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 50px;width:740px;text-align:center;">
              其他工作及成果
            </td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 50px;width:240px;text-align:center;">
              {{customApplyFirst.qtgzjcg}}
            </td>
          </tr>
        </table>
      </template>
      <template v-if="currentIndex==8  || currentVisiable">
        <table class="customTable">
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 60px; width:740px;text-align:center;">
              拟聘岗位工作思路及预期目标（限2000字以内）
            </td>
          </tr>
          <tr>
            <td style="border-top: 1px solid black;border-left: 1px solid black;height: 800px;text-align:left;">
              {{customApplyFirst.npgwgzsljyqmb}}
            </td>
          </tr>
        </table>
      </template>
    </div>
  </a-card>
</template>

<script>
export default {
  name: 'sss',
  computed: {
    dcaBEssaypublishListLeft () {
      return 11 - this.customApplyFirst.dcaBEssaypublishList.length
    },
    dcaBSciencesearchListLeft () {
      return 10 - this.customApplyFirst.dcaBSciencesearchList.length
    },
    dcaBScientificprizeListLeft () {
      return 10 - this.customApplyFirst.dcaBScientificprizeList.length
    },
    dcaBPatentListLeft () {
      return 10 - this.customApplyFirst.dcaBPatentList.length
    }
  },
  methods: {
    Previou () {
      if (this.currentIndex > 1) {
        this.currentIndex = this.currentIndex - 1
      }
    },
    Next () {
      if (this.currentIndex < 8) {
        this.currentIndex = this.currentIndex + 1
      }
    },
    generatePdf () {
      this.$getPdf(document.querySelector('#printpdf'), "20109")
    }
  },
  data () {
    return {
      currentIndex: 8,
      currentVisiable: true,
      customApplyFirst: {
        rsbh: '234344',
        name: ' 李帅护士',
        xgwzw: '经理室',
        npgwzw: '董事长',
        gwlb: '岗位类别',
        xzyjsgw: '高级心理辅导',
        sbnpgwly: '申报拟聘岗位理由',
        grsxzzjsdsf: '个人细想政治及师德师风表现情况',
        jszgzbhjhdsj: '教师资格证编号及获得时间',
        drfdyjsbzrjkhqk: '担任辅导员教师班主任及考核情况',
        wcsypqgzrwqk: '完成上一聘期工作任务情况',
        prsj: '2020-09-08',
        lxgzsj: '2008-09-07',
        xcszyjzc: '从事网络设计与心理辅导',
        shjz: '从事老你男人的速度',
        hshdshjljcf: '斯蒂芬速度廊坊市东风里看idsfkdsfdsfdsfsadfdsfdssdsads',
        j5nkhqk: '犯罪记录明显',
        dcaBGraduate: {},
        qtgzjcg: '其他工作及成果',
        npgwgzsljyqmb: '拟聘岗位工作思路及预期目标',
        dcaBEducationexpericeList: [
          {
            expStartTime: '2001-09-08',
            expEndTime: '2009-09-08',
            expAddress: '清华大学',
            expSchool: '清华园',
            expPosition: '扫地层',
            expCertifier: '楼管'
          },
          {
            expStartTime: '2001-09-08',
            expEndTime: '2009-09-08',
            expAddress: '清华大学',
            expSchool: '清华园',
            expPosition: '扫地层',
            expCertifier: '楼管'
          }
        ],
        dcaBUndergraduateList: [],
        dcaBTeachtalentList: [],
        dcaBInnovatebuildList: [],
        dcaBPaperspublishList: [],
        dcaBTalentList: [],
        dcaBSciencepublishList: [],
        dcaBEssaypublishList: [{
          essayName: '北极光不属于的',
          eassyJournalname: '9月期刊武汉分刊',
          eassyPublishname: '武汉九只老虎',
          eassyStartpage: '28',
          eassyEndpage: '29',
          eassyJournalcode: '2020-09',
          eassyJournalgrade: '低级趣味',
          eassyPublishdate: '202009',
          eassyRankname: '3'
        }, {
          essayName: '北极光不属于的',
          eassyJournalname: '9月期刊武汉分刊2',
          eassyPublishname: '武汉九只老虎2',
          eassyStartpage: '28',
          eassyEndpage: '29',
          eassyJournalcode: '2020-09',
          eassyJournalgrade: '低级趣味2',
          eassyPublishdate: '202009',
          eassyRankname: '3'
        }, {
          eassyJournalname: '9月期刊武汉分刊3',
          eassyPublishname: '武汉九只老虎3',
          eassyStartpage: '28',
          eassyEndpage: '29',
          eassyJournalcode: '2020-09',
          eassyJournalgrade: '低级趣味3',
          eassyPublishdate: '202009',
          eassyRankname: '3'
        }],
        grsxzzjsdsf: 'dsfdsfs撒阿斯发撒旦是否第三方撒的发合适的看法宽度是否开机后撒的发空间撒的发看见对方',
        dcaBSciencesearchList: [],
        dcaBScientificprizeList: [],
        dcaBPatentList: [],
        grzj: ''

      },
      experciceTr: '主要学习及工作经历（从本科开始填写，含国内外进修情况，按时间正序连续填写）'
    }
  }
}
</script>

<style type="text/css">
.lefttxt {
  border-bottom: 2px solid black;
  text-align: center;
  width: 300px;
  height: 80px;
  position: relative;
}
.lefttxt p {
  text-align: center;
  font-size: 20px !important;
  position: absolute;
  bottom: 0px;
  padding: 0px;
  width: 300px;
  margin: 0px;
}
.nametxt {
  text-align: center;
  height: 80px;
  position: relative;
}
.nametxt p {
  width: 100%;
  font-weight: bold;
  text-align: center;
  font-size: 20px !important;
  position: absolute;
  bottom: 0px;
  padding: 0px;
  margin: 0px;
}

.customLeftTd {
  border-top: 1px solid black;
  border-left: 1px solid black;
}
.customTable {
  border-right: 1px solid black;
  border-bottom: 1px solid black;
}
</style>