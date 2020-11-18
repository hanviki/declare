<template>
  <a-card title="">
    <div>
      <a-button
        @click="handleAdd"
        type="primary"
        :loading="loading"
      >添加行</a-button>
      <a-button
        @click="handleDelete"
        type="primary"
        :loading="loading"
      >删除行</a-button>
    </div>
    <a-table
      :columns="columns"
      :data-source="dataSource"
      :rowKey="record => record.id"
      :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
      bordered
    >
      <template
        slot="year"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'year')"
            :value="record.year"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="confirmIndex"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'confirmIndex')"
            :value="record.confirmIndex"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="danganIndex"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'danganIndex')"
            :value="record.danganIndex"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="baomingIndex"
        slot-scope="text, record"
      >
        <div v-if="record.state==3">
          {{text}}
        </div>
        <div v-else>
          <a-input-number
            @blur="e => inputChange(e.target.value,record,'baomingIndex')"
            :value="record.baomingIndex"
            :precision="0"
          >
          </a-input-number>
        </div>
      </template>
      <template
        slot="xl"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'xl')"
            :value="record.xl"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="pingshenfenzu"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'pingshenfenzu')"
            :value="record.pingshenfenzu"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="ifshuangbao"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'ifshuangbao')"
            :value="record.ifshuangbao"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="gwdj"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'gwdj')"
            :value="record.gwdj"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="ks"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'ks')"
            :value="record.ks"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="kslb"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'kslb')"
            :value="record.kslb"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="birthdaystr"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'birthdaystr')"
            :value="record.birthdaystr"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="age"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'age')"
            :value="record.age"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="sexName"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'sexName')"
            :value="record.sexName"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="edu"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'edu')"
            :value="record.edu"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="eduDate"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'eduDate')"
            :value="record.eduDate"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="positionName"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'positionName')"
            :value="record.positionName"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="zygwDate"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'zygwDate')"
            :value="record.zygwDate"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="npPositionName"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'npPositionName')"
            :value="record.npPositionName"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="schoolDate"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'schoolDate')"
            :value="record.schoolDate"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="ifdaitou"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'ifdaitou')"
            :value="record.ifdaitou"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="ylpfbfz"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'ylpfbfz')"
            :value="record.ylpfbfz"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="jxpf"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'jxpf')"
            :value="record.jxpf"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="iffuhekeyan"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'iffuhekeyan')"
            :value="record.iffuhekeyan"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="iffuhediyi"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'iffuhediyi')"
            :value="record.iffuhediyi"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="iffuhebibei"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'iffuhebibei')"
            :value="record.iffuhebibei"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="sciName"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'sciName')"
            :value="record.sciName"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="sciDengji"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'sciDengji')"
            :value="record.sciDengji"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="sciRanknum"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'sciRanknum')"
            :value="record.sciRanknum"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="teachName"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'teachName')"
            :value="record.teachName"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="teachDengji"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'teachDengji')"
            :value="record.teachDengji"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="teachRanknum"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'teachRanknum')"
            :value="record.teachRanknum"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="patentNum"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'patentNum')"
            :value="record.patentNum"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="patentFund"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'patentFund')"
            :value="record.patentFund"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="publishA"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'publishA')"
            :value="record.publishA"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="publishB"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'publishB')"
            :value="record.publishB"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="publishC"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'publishC')"
            :value="record.publishC"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="publishD"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'publishD')"
            :value="record.publishD"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="publishE"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'publishE')"
            :value="record.publishE"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="publishF"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'publishF')"
            :value="record.publishF"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="publishDup"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'publishDup')"
            :value="record.publishDup"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="publishEup"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'publishEup')"
            :value="record.publishEup"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="publishFup"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'publishFup')"
            :value="record.publishFup"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="publicarticle1"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'publicarticle1')"
            :value="record.publicarticle1"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="publicarticle2"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'publicarticle2')"
            :value="record.publicarticle2"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="schoolprizeName"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'schoolprizeName')"
            :value="record.schoolprizeName"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="schoolprizeDengji"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'schoolprizeDengji')"
            :value="record.schoolprizeDengji"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="schoolprizeRanknum"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'schoolprizeRanknum')"
            :value="record.schoolprizeRanknum"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="schoolprizeDate"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'schoolprizeDate')"
            :value="record.schoolprizeDate"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="courseName"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'courseName')"
            :value="record.courseName"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="courseRanknum"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'courseRanknum')"
            :value="record.courseRanknum"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="courseDate"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'courseDate')"
            :value="record.courseDate"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="youngName"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'youngName')"
            :value="record.youngName"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="youngDengji"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'youngDengji')"
            :value="record.youngDengji"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="youngRanknum"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'youngRanknum')"
            :value="record.youngRanknum"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="youngDate"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'youngDate')"
            :value="record.youngDate"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="sciDjlb"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'sciDjlb')"
            :value="record.sciDjlb"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="sciDjfund"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'sciDjfund')"
            :value="record.sciDjfund"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="sciDjranknum"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'sciDjranknum')"
            :value="record.sciDjranknum"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="sciJflb"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'sciJflb')"
            :value="record.sciJflb"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="sciJffund"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'sciJffund')"
            :value="record.sciJffund"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="sciJfranknum"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'sciJfranknum')"
            :value="record.sciJfranknum"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="ylpfdj"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'ylpfdj')"
            :value="record.ylpfdj"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="jxpfdj"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'jxpfdj')"
            :value="record.jxpfdj"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="pfHeji"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'pfHeji')"
            :value="record.pfHeji"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="tutor"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'tutor')"
            :value="record.tutor"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="sblx"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'sblx')"
            :value="record.sblx"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="choosepos"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'choosepos')"
            :value="record.choosepos"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="clshjg"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'clshjg')"
            :value="record.clshjg"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="ntyy"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'ntyy')"
            :value="record.ntyy"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="ksrank"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'ksrank')"
            :value="record.ksrank"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="teacherQualify"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'teacherQualify')"
            :value="record.teacherQualify"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="npqk"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'npqk')"
            :value="record.npqk"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="borad"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'borad')"
            :value="record.borad"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="note"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'note')"
            :value="record.note"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="telephone"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'telephone')"
            :value="record.telephone"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="courseDengji"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          {{text}}
        </div>
        <div v-else>
          <a-textarea
            @blur="e => inputChange(e.target.value,record,'courseDengji')"
            :value="record.courseDengji"
          >
          </a-textarea>
        </div>
      </template>
      <template
        slot="fileId"
        slot-scope="text, record"
      >
        <div v-if="record.state==3 || record.state==1">
          <a
            :href="record.fileUrl"
            v-if="text!=null && text !=''"
            target="_blank"
          >查看</a>
        </div>
        <div v-else>
          <a-button
            type="dashed"
            block
            @click="OpenFile(record)"
          >
            {{record.fileId!=null &&record.fileId !=''?'已上传':'上传' }}
          </a-button>
        </div>
      </template>
      <template
        slot="isUse"
        slot-scope="text, record"
      >
        <a-checkbox
          @change="e => onIsUseChange(e,record,'isUse')"
          :checked="text"
        ></a-checkbox>
      </template>
    </a-table>
    <div>
      <a-button
        @click="handleSave"
        type="primary"
        :loading="loading"
      >保存草稿</a-button>
      <a-button
        @click="handleSubmit"
        type="primary"
        :loading="loading"
      >提交</a-button>
    </div>
    <tableUpload-file
      ref="upFile"
      :fileId="editRecord.fileId"
      :fileVisiable="fileVisiable"
      @setFileId="setFileId"
    >
    </tableUpload-file>
  </a-card>
</template>

<script>
import moment from 'moment';
import TableUploadFile from '../../common/TableUploadFile'
export default {
  data () {
    return {
      dateFormat: 'YYYY-MM-DD',
      dataSource: [],
      selectedRowKeys: [],
      loading: false,
      CustomVisiable: false,
      idNums: 10000,
      fileVisiable: false,
      editRecord: {
        fileId: ''
      }
    }
  },
  components: { TableUploadFile },
  mounted () {
    this.fetch()
  },
  methods: {
    moment,
    showFile (record) {
      window.location.href = record.fileUrl
    },
    OpenFile (record) {
      this.editRecord = record
      this.fileVisiable = true
      if (record.fileId != undefined && record.fileId != '') {
        this.$refs.upFile.fetch(record.fileId)
      }
    },
    setFileId (fileId, fileUrl) {
      this.fileVisiable = false
      console.log(fileUrl)
      /**
       const dataSource = [...this.dataSource]
       console.log(this.editRecord.id)
       let record=dataSource.filter(p=>p.id===this.editRecord.id)
       console.log(record)*/
      this.editRecord["fileId"] = fileId
      this.editRecord["fileUrl"] = fileUrl
      //this.dataSource =[...dataSource]
    },
    onSelectChange (selectedRowKeys, selectedRows) {
      // console.log(selectedRows)
      if (selectedRows.length > 0) {
        if (selectedRows[0].state != 3 && selectedRows[0].state != 1) {
          this.selectedRowKeys = selectedRowKeys
        }
      }
      else {
        this.selectedRowKeys = selectedRowKeys
      }
    },
    handleChange (date, dateStr, record, filedName) {
      const value = dateStr
      record[filedName] = value
    },
    inputChange (value, record, filedName) {
      console.info(value)
      record[filedName] = value
    },
    handleSelectChange (value, option, record, filedName) {
      console.info(value)
      record[filedName] = value
    },
    onIsUseChange (e, record, filedName) {
      record[filedName] = e.target.checked;
    },
    handleAdd () {
      for (let i = 0; i < 4; i++) {
        this.dataSource.push({
          id: (this.idNums + i + 1).toString(),
          state: 0,
          year: '',
          confirmIndex: '',
          danganIndex: '',
          baomingIndex: '',
          xl: '',
          pingshenfenzu: '',
          ifshuangbao: '',
          gwdj: '',
          ks: '',
          kslb: '',
          birthdaystr: '',
          age: '',
          sexName: '',
          edu: '',
          eduDate: '',
          positionName: '',
          zygwDate: '',
          npPositionName: '',
          schoolDate: '',
          ifdaitou: '',
          ylpfbfz: '',
          jxpf: '',
          iffuhekeyan: '',
          iffuhediyi: '',
          iffuhebibei: '',
          sciName: '',
          sciDengji: '',
          sciRanknum: '',
          teachName: '',
          teachDengji: '',
          teachRanknum: '',
          patentNum: '',
          patentFund: '',
          publishA: '',
          publishB: '',
          publishC: '',
          publishD: '',
          publishE: '',
          publishF: '',
          publishDup: '',
          publishEup: '',
          publishFup: '',
          publicarticle1: '',
          publicarticle2: '',
          schoolprizeName: '',
          schoolprizeDengji: '',
          schoolprizeRanknum: '',
          schoolprizeDate: '',
          courseName: '',
          courseRanknum: '',
          courseDate: '',
          youngName: '',
          youngDengji: '',
          youngRanknum: '',
          youngDate: '',
          sciDjlb: '',
          sciDjfund: '',
          sciDjranknum: '',
          sciJflb: '',
          sciJffund: '',
          sciJfranknum: '',
          ylpfdj: '',
          jxpfdj: '',
          pfHeji: '',
          tutor: '',
          sblx: '',
          choosepos: '',
          clshjg: '',
          ntyy: '',
          ksrank: '',
          teacherQualify: '',
          npqk: '',
          borad: '',
          note: '',
          telephone: '',
          courseDengji: '',
          isUse: false
        })
      }
      this.idNums = this.idNums + 4
    },
    handleSave () {
      const dataSourceAll = [...this.dataSource]
      const dataSource = dataSourceAll.filter(p => p.state == 0 || p.state == 2)
      let dataAdd = []
      dataSource.forEach(element => {
        if (element.year != '' || element.confirmIndex != '' || element.danganIndex != '' || element.baomingIndex != '' || element.xl != '' || element.pingshenfenzu != '' || element.ifshuangbao != '' || element.gwdj != '' || element.ks != '' || element.kslb != '' || element.birthdaystr != '' || element.age != '' || element.sexName != '' || element.edu != '' || element.eduDate != '' || element.positionName != '' || element.zygwDate != '' || element.npPositionName != '' || element.schoolDate != '' || element.ifdaitou != '' || element.ylpfbfz != '' || element.jxpf != '' || element.iffuhekeyan != '' || element.iffuhediyi != '' || element.iffuhebibei != '' || element.sciName != '' || element.sciDengji != '' || element.sciRanknum != '' || element.teachName != '' || element.teachDengji != '' || element.teachRanknum != '' || element.patentNum != '' || element.patentFund != '' || element.publishA != '' || element.publishB != '' || element.publishC != '' || element.publishD != '' || element.publishE != '' || element.publishF != '' || element.publishDup != '' || element.publishEup != '' || element.publishFup != '' || element.publicarticle1 != '' || element.publicarticle2 != '' || element.schoolprizeName != '' || element.schoolprizeDengji != '' || element.schoolprizeRanknum != '' || element.schoolprizeDate != '' || element.courseName != '' || element.courseRanknum != '' || element.courseDate != '' || element.youngName != '' || element.youngDengji != '' || element.youngRanknum != '' || element.youngDate != '' || element.sciDjlb != '' || element.sciDjfund != '' || element.sciDjranknum != '' || element.sciJflb != '' || element.sciJffund != '' || element.sciJfranknum != '' || element.ylpfdj != '' || element.jxpfdj != '' || element.pfHeji != '' || element.tutor != '' || element.sblx != '' || element.choosepos != '' || element.clshjg != '' || element.ntyy != '' || element.ksrank != '' || element.teacherQualify != '' || element.npqk != '' || element.borad != '' || element.note != '' || element.telephone != '' || element.courseDengji != '') {
          dataAdd.push(element)
        }
      });
      if (dataAdd.length === 0) {
        this.$message.warning('请填写数据！！！')
      }
      else {
        let jsonStr = JSON.stringify(dataAdd)
        this.loading = true
        this.$post('dcaBReport/addNew', {
          jsonStr: jsonStr,
          state: 0
        }).then(() => {
          // this.reset()
          this.$message.success('保存成功')
          this.fetch()
          this.loading = false
        }).catch(() => {
          this.loading = false
        })
      }
    },
    handleSubmit () {
      let that = this
      this.$confirm({
        title: '确定提交全部记录?',
        content: '当您点击确定按钮后，信息将不能修改',
        centered: true,
        onOk () {
          const dataSourceAll = [...that.dataSource]
          const dataSource = dataSourceAll.filter(p => p.state == 0 || p.state == 2)
          let dataAdd = []
          dataSource.forEach(element => {
            if (element.year != '' || element.confirmIndex != '' || element.danganIndex != '' || element.baomingIndex != '' || element.xl != '' || element.pingshenfenzu != '' || element.ifshuangbao != '' || element.gwdj != '' || element.ks != '' || element.kslb != '' || element.birthdaystr != '' || element.age != '' || element.sexName != '' || element.edu != '' || element.eduDate != '' || element.positionName != '' || element.zygwDate != '' || element.npPositionName != '' || element.schoolDate != '' || element.ifdaitou != '' || element.ylpfbfz != '' || element.jxpf != '' || element.iffuhekeyan != '' || element.iffuhediyi != '' || element.iffuhebibei != '' || element.sciName != '' || element.sciDengji != '' || element.sciRanknum != '' || element.teachName != '' || element.teachDengji != '' || element.teachRanknum != '' || element.patentNum != '' || element.patentFund != '' || element.publishA != '' || element.publishB != '' || element.publishC != '' || element.publishD != '' || element.publishE != '' || element.publishF != '' || element.publishDup != '' || element.publishEup != '' || element.publishFup != '' || element.publicarticle1 != '' || element.publicarticle2 != '' || element.schoolprizeName != '' || element.schoolprizeDengji != '' || element.schoolprizeRanknum != '' || element.schoolprizeDate != '' || element.courseName != '' || element.courseRanknum != '' || element.courseDate != '' || element.youngName != '' || element.youngDengji != '' || element.youngRanknum != '' || element.youngDate != '' || element.sciDjlb != '' || element.sciDjfund != '' || element.sciDjranknum != '' || element.sciJflb != '' || element.sciJffund != '' || element.sciJfranknum != '' || element.ylpfdj != '' || element.jxpfdj != '' || element.pfHeji != '' || element.tutor != '' || element.sblx != '' || element.choosepos != '' || element.clshjg != '' || element.ntyy != '' || element.ksrank != '' || element.teacherQualify != '' || element.npqk != '' || element.borad != '' || element.note != '' || element.telephone != '' || element.courseDengji != '') {
              dataAdd.push(element)
            }
          });
          if (dataAdd.length === 0) {
            that.$message.warning('请填写数据！！！')
          }
          else {
            let jsonStr = JSON.stringify(dataAdd)
            that.loading = true
            that.$post('dcaBReport/addNew', {
              jsonStr: jsonStr,
              state: 1
            }).then(() => {
              //this.reset()
              that.$message.success('提交成功')
              that.fetch()
              that.CustomVisiable = false //提交之后 不能再修改
              that.loading = false
            }).catch(() => {
              that.loading = false
            })
          }
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })


    },
    handleDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let dcaBPatentIds = that.selectedRowKeys.join(',')
          const dataSource = [...that.dataSource];
          let new_dataSource = dataSource.filter(p => that.selectedRowKeys.indexOf(p.id) < 0)
          that.dataSource = new_dataSource
          that.$message.success('删除成功')
          that.selectedRowKeys = []
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    fetch () {
      this.$get('dcaBReport/custom', {
      }).then((r) => {
        let data = r.data
        this.dataSource = data.rows

        for (let i = 0; i < 4; i++) {
          this.dataSource.push({
            id: (this.idNums + i + 1).toString(),
            state: 0,
            year: '',
            confirmIndex: '',
            danganIndex: '',
            baomingIndex: '',
            xl: '',
            pingshenfenzu: '',
            ifshuangbao: '',
            gwdj: '',
            ks: '',
            kslb: '',
            birthdaystr: '',
            age: '',
            sexName: '',
            edu: '',
            eduDate: '',
            positionName: '',
            zygwDate: '',
            npPositionName: '',
            schoolDate: '',
            ifdaitou: '',
            ylpfbfz: '',
            jxpf: '',
            iffuhekeyan: '',
            iffuhediyi: '',
            iffuhebibei: '',
            sciName: '',
            sciDengji: '',
            sciRanknum: '',
            teachName: '',
            teachDengji: '',
            teachRanknum: '',
            patentNum: '',
            patentFund: '',
            publishA: '',
            publishB: '',
            publishC: '',
            publishD: '',
            publishE: '',
            publishF: '',
            publishDup: '',
            publishEup: '',
            publishFup: '',
            publicarticle1: '',
            publicarticle2: '',
            schoolprizeName: '',
            schoolprizeDengji: '',
            schoolprizeRanknum: '',
            schoolprizeDate: '',
            courseName: '',
            courseRanknum: '',
            courseDate: '',
            youngName: '',
            youngDengji: '',
            youngRanknum: '',
            youngDate: '',
            sciDjlb: '',
            sciDjfund: '',
            sciDjranknum: '',
            sciJflb: '',
            sciJffund: '',
            sciJfranknum: '',
            ylpfdj: '',
            jxpfdj: '',
            pfHeji: '',
            tutor: '',
            sblx: '',
            choosepos: '',
            clshjg: '',
            ntyy: '',
            ksrank: '',
            teacherQualify: '',
            npqk: '',
            borad: '',
            note: '',
            telephone: '',
            courseDengji: '',
            isUse: false
          })
          this.idNums = this.idNums + 4
        }
      })
    }
  },
  computed: {
    columns () {
      return [{
        title: '起止年度',
        dataIndex: 'year',
        width: 130,
        scopedSlots: { customRender: 'year' }
      },
      {
        title: '确认顺序号',
        dataIndex: 'confirmIndex',
        width: 130,
        scopedSlots: { customRender: 'confirmIndex' }
      },
      {
        title: '档案袋顺序号',
        dataIndex: 'danganIndex',
        width: 130,
        scopedSlots: { customRender: 'danganIndex' }
      },
      {
        title: '报名顺序号',
        dataIndex: 'baomingIndex',
        width: 130,
        scopedSlots: { customRender: 'baomingIndex' }
      },
      {
        title: '系列',
        dataIndex: 'xl',
        width: 130,
        scopedSlots: { customRender: 'xl' }
      },
      {
        title: '评审分组',
        dataIndex: 'pingshenfenzu',
        width: 130,
        scopedSlots: { customRender: 'pingshenfenzu' }
      },
      {
        title: '双报标志',
        dataIndex: 'ifshuangbao',
        width: 130,
        scopedSlots: { customRender: 'ifshuangbao' }
      },
      {
        title: '申报等级',
        dataIndex: 'gwdj',
        width: 130,
        scopedSlots: { customRender: 'gwdj' }
      },
      {
        title: '科室',
        dataIndex: 'ks',
        width: 130,
        scopedSlots: { customRender: 'ks' }
      },
      {
        title: '科室分类',
        dataIndex: 'kslb',
        width: 130,
        scopedSlots: { customRender: 'kslb' }
      },
      {
        title: '出生年月',
        dataIndex: 'birthdaystr',
        width: 130,
        scopedSlots: { customRender: 'birthdaystr' }
      },
      {
        title: '年龄',
        dataIndex: 'age',
        width: 130,
        scopedSlots: { customRender: 'age' }
      },
      {
        title: '性别',
        dataIndex: 'sexName',
        width: 130,
        scopedSlots: { customRender: 'sexName' }
      },
      {
        title: '学历(位)',
        dataIndex: 'edu',
        width: 130,
        scopedSlots: { customRender: 'edu' }
      },
      {
        title: '毕业时间',
        dataIndex: 'eduDate',
        width: 130,
        scopedSlots: { customRender: 'eduDate' }
      },
      {
        title: '现职务名称',
        dataIndex: 'positionName',
        width: 130,
        scopedSlots: { customRender: 'positionName' }
      },
      {
        title: '聘任时间',
        dataIndex: 'zygwDate',
        width: 130,
        scopedSlots: { customRender: 'zygwDate' }
      },
      {
        title: '申报职称',
        dataIndex: 'npPositionName',
        width: 130,
        scopedSlots: { customRender: 'npPositionName' }
      },
      {
        title: '来院时间',
        dataIndex: 'schoolDate',
        width: 130,
        scopedSlots: { customRender: 'schoolDate' }
      },
      {
        title: '是否起带头或骨干作用',
        dataIndex: 'ifdaitou',
        width: 130,
        scopedSlots: { customRender: 'ifdaitou' }
      },
      {
        title: '医疗评分',
        dataIndex: 'ylpfbfz',
        width: 130,
        scopedSlots: { customRender: 'ylpfbfz' }
      },
      {
        title: '教学评分',
        dataIndex: 'jxpf',
        width: 130,
        scopedSlots: { customRender: 'jxpf' }
      },
      {
        title: '教学科研项目或获奖情况是否符合',
        dataIndex: 'iffuhekeyan',
        width: 130,
        scopedSlots: { customRender: 'iffuhekeyan' }
      },
      {
        title: '第一作者论文情况是否符合',
        dataIndex: 'iffuhediyi',
        width: 130,
        scopedSlots: { customRender: 'iffuhediyi' }
      },
      {
        title: '是否符合必备条件',
        dataIndex: 'iffuhebibei',
        width: 130,
        scopedSlots: { customRender: 'iffuhebibei' }
      },
      {
        title: '国家、省部级科研奖名称',
        dataIndex: 'sciName',
        width: 130,
        scopedSlots: { customRender: 'sciName' }
      },
      {
        title: '国家、省部级科研奖等级',
        dataIndex: 'sciDengji',
        width: 130,
        scopedSlots: { customRender: 'sciDengji' }
      },
      {
        title: '国家、省部级科研奖排名',
        dataIndex: 'sciRanknum',
        width: 130,
        scopedSlots: { customRender: 'sciRanknum' }
      },
      {
        title: '教学名称',
        dataIndex: 'teachName',
        width: 130,
        scopedSlots: { customRender: 'teachName' }
      },
      {
        title: '教学等级',
        dataIndex: 'teachDengji',
        width: 130,
        scopedSlots: { customRender: 'teachDengji' }
      },
      {
        title: '教学排名',
        dataIndex: 'teachRanknum',
        width: 130,
        scopedSlots: { customRender: 'teachRanknum' }
      },
      {
        title: '发明专利项数',
        dataIndex: 'patentNum',
        width: 130,
        scopedSlots: { customRender: 'patentNum' }
      },
      {
        title: '实施转让费',
        dataIndex: 'patentFund',
        width: 130,
        scopedSlots: { customRender: 'patentFund' }
      },
      {
        title: 'A 类',
        dataIndex: 'publishA',
        width: 130,
        scopedSlots: { customRender: 'publishA' }
      },
      {
        title: 'B 类',
        dataIndex: 'publishB',
        width: 130,
        scopedSlots: { customRender: 'publishB' }
      },
      {
        title: 'C 类',
        dataIndex: 'publishC',
        width: 130,
        scopedSlots: { customRender: 'publishC' }
      },
      {
        title: 'D 类',
        dataIndex: 'publishD',
        width: 130,
        scopedSlots: { customRender: 'publishD' }
      },
      {
        title: 'E 类',
        dataIndex: 'publishE',
        width: 130,
        scopedSlots: { customRender: 'publishE' }
      },
      {
        title: 'F 类',
        dataIndex: 'publishF',
        width: 130,
        scopedSlots: { customRender: 'publishF' }
      },
      {
        title: 'D类以上',
        dataIndex: 'publishDup',
        width: 130,
        scopedSlots: { customRender: 'publishDup' }
      },
      {
        title: 'E类以上',
        dataIndex: 'publishEup',
        width: 130,
        scopedSlots: { customRender: 'publishEup' }
      },
      {
        title: 'F类以上',
        dataIndex: 'publishFup',
        width: 130,
        scopedSlots: { customRender: 'publishFup' }
      },
      {
        title: '出版书类别',
        dataIndex: 'publicarticle1',
        width: 130,
        scopedSlots: { customRender: 'publicarticle1' }
      },
      {
        title: '承担字数(万)',
        dataIndex: 'publicarticle2',
        width: 130,
        scopedSlots: { customRender: 'publicarticle2' }
      },
      {
        title: '教学质量奖与成果奖名称',
        dataIndex: 'schoolprizeName',
        width: 130,
        scopedSlots: { customRender: 'schoolprizeName' }
      },
      {
        title: '等级',
        dataIndex: 'schoolprizeDengji',
        width: 130,
        scopedSlots: { customRender: 'schoolprizeDengji' }
      },
      {
        title: '排名',
        dataIndex: 'schoolprizeRanknum',
        width: 130,
        scopedSlots: { customRender: 'schoolprizeRanknum' }
      },
      {
        title: '时间',
        dataIndex: 'schoolprizeDate',
        width: 130,
        scopedSlots: { customRender: 'schoolprizeDate' }
      },
      {
        title: '精品课程名称',
        dataIndex: 'courseName',
        width: 130,
        scopedSlots: { customRender: 'courseName' }
      },
      {
        title: '排名',
        dataIndex: 'courseRanknum',
        width: 130,
        scopedSlots: { customRender: 'courseRanknum' }
      },
      {
        title: '时间',
        dataIndex: 'courseDate',
        width: 130,
        scopedSlots: { customRender: 'courseDate' }
      },
      {
        title: '教学竞赛获奖奖项级别',
        dataIndex: 'youngName',
        width: 130,
        scopedSlots: { customRender: 'youngName' }
      },
      {
        title: '等级',
        dataIndex: 'youngDengji',
        width: 130,
        scopedSlots: { customRender: 'youngDengji' }
      },
      {
        title: '排名',
        dataIndex: 'youngRanknum',
        width: 130,
        scopedSlots: { customRender: 'youngRanknum' }
      },
      {
        title: '时间',
        dataIndex: 'youngDate',
        width: 130,
        scopedSlots: { customRender: 'youngDate' }
      },
      {
        title: '科研项目教改项目类别',
        dataIndex: 'sciDjlb',
        width: 130,
        scopedSlots: { customRender: 'sciDjlb' }
      },
      {
        title: '经费',
        dataIndex: 'sciDjfund',
        width: 130,
        scopedSlots: { customRender: 'sciDjfund' }
      },
      {
        title: '排名',
        dataIndex: 'sciDjranknum',
        width: 130,
        scopedSlots: { customRender: 'sciDjranknum' }
      },
      {
        title: '实到校单项科研经费类别',
        dataIndex: 'sciJflb',
        width: 130,
        scopedSlots: { customRender: 'sciJflb' }
      },
      {
        title: '实到校单项科研经费类别',
        dataIndex: 'sciJffund',
        width: 130,
        scopedSlots: { customRender: 'sciJffund' }
      },
      {
        title: '排名',
        dataIndex: 'sciJfranknum',
        width: 130,
        scopedSlots: { customRender: 'sciJfranknum' }
      },
      {
        title: '医疗评分等级',
        dataIndex: 'ylpfdj',
        width: 130,
        scopedSlots: { customRender: 'ylpfdj' }
      },
      {
        title: '教学评分等级',
        dataIndex: 'jxpfdj',
        width: 130,
        scopedSlots: { customRender: 'jxpfdj' }
      },
      {
        title: '评分合计',
        dataIndex: 'pfHeji',
        width: 130,
        scopedSlots: { customRender: 'pfHeji' }
      },
      {
        title: '是否担任一年辅导员或班主任',
        dataIndex: 'tutor',
        width: 130,
        scopedSlots: { customRender: 'tutor' }
      },
      {
        title: '申报类型',
        dataIndex: 'sblx',
        width: 130,
        scopedSlots: { customRender: 'sblx' }
      },
      {
        title: '达到选择条件一之第几条',
        dataIndex: 'choosepos',
        width: 130,
        scopedSlots: { customRender: 'choosepos' }
      },
      {
        title: '材料审核结果',
        dataIndex: 'clshjg',
        width: 130,
        scopedSlots: { customRender: 'clshjg' }
      },
      {
        title: '拟退原因',
        dataIndex: 'ntyy',
        width: 130,
        scopedSlots: { customRender: 'ntyy' }
      },
      {
        title: '科室排名',
        dataIndex: 'ksrank',
        width: 130,
        scopedSlots: { customRender: 'ksrank' }
      },
      {
        title: '教师资格证',
        dataIndex: 'teacherQualify',
        width: 130,
        scopedSlots: { customRender: 'teacherQualify' }
      },
      {
        title: '内聘情况',
        dataIndex: 'npqk',
        width: 130,
        scopedSlots: { customRender: 'npqk' }
      },
      {
        title: '出国情况',
        dataIndex: 'borad',
        width: 130,
        scopedSlots: { customRender: 'borad' }
      },
      {
        title: '备注',
        dataIndex: 'note',
        width: 130,
        scopedSlots: { customRender: 'note' }
      },
      {
        title: '联系方式',
        dataIndex: 'telephone',
        width: 130,
        scopedSlots: { customRender: 'telephone' }
      },
      {
        title: '等级',
        dataIndex: 'courseDengji',
        width: 130,
        scopedSlots: { customRender: 'courseDengji' }
      },
      {
        title: '状态',
        dataIndex: 'state',
        width: 100,
        customRender: (text, row, index) => {
          switch (text) {
            case 0:
              return <a-tag color="purple">未提交</a-tag>
            case 1:
              return <a-tag color="green">已提交</a-tag>
            case 2:
              return <a-tag color="red">审核未通过</a-tag>
            case 3:
              return <a-tag color="#f50">已审核</a-tag>
            default:
              return text
          }
        }
      },
      {
        title: '审核意见',
        dataIndex: 'auditSuggestion'
      },
      {
        title: '是否用于本次评审',
        dataIndex: 'isUse',
        scopedSlots: { customRender: 'isUse' },
        width: 80
      },
      {
        title: '附件',
        dataIndex: 'fileId',
        scopedSlots: { customRender: 'fileId' },
        width: 80
      }]
    }
  },
}
</script>

<style lang="less" scoped>
@import "../../../../static/less/Common";
</style>
