<template>
  <div>
    <div>
      <div style="display: flex; justify-content: space-between">
        <div>
          <el-input
            placeholder="请输入车牌号进行搜索，可以直接回车搜索..."
            prefix-icon="el-icon-search"
            clearable
            @clear="initEmps"
            style="width: 350px; margin-right: 10px"
            v-model="keyword"
            @keydown.enter.native="initEmps"
            :disabled="showAdvanceSearchView"
          ></el-input>
          <el-button
            icon="el-icon-search"
            type="primary"
            @click="initEmps"
            :disabled="showAdvanceSearchView"
          >
            搜索
          </el-button>
        </div>
      </div>
    </div>
    <div style="margin-top: 10px">
      <el-table
        :data="emps"
        stripe
        border
        v-loading="loading"
        element-loading-text="正在加载..."
        element-loading-spinner="el-icon-loading"
        element-loading-background="rgba(0, 0, 0, 0.8)"
        style="width: 100%"
      >
        <el-table-column type="selection" width="55"> </el-table-column>
        <el-table-column
          prop="enVlp"
          fixed
          align="left"
          label="车牌号"
          width="150"
        >
        </el-table-column>
        <el-table-column
          prop="enVlpc"
          label="车牌颜色"
          align="left"
          width="150"
        >
        </el-table-column>
        <el-table-column
          prop="entollStationname"
          label="入口站"
          align="left"
          width="150"
        >
        </el-table-column>
        <el-table-column
          prop="extollStationname"
          width="150"
          align="left"
          label="出口站"
        >
        </el-table-column>
        <el-table-column
          prop="exTime"
          width="150"
          align="left"
          label="出口时间"
        >
        </el-table-column>
        <el-table-column prop="reMarks" width="300" label="标记">
        </el-table-column>
        <el-table-column fixed="right" width="200" label="操作">
          <template slot-scope="scope">
            <el-button
              @click="showEditEmpView(scope.row)"
              style="padding: 3px"
              size="mini"
              >编辑</el-button
            >
            <el-button
              @click="deleteEmp(scope.row)"
              style="padding: 3px"
              size="mini"
              type="danger"
              >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <div style="display: flex; justify-content: flex-end">
        <el-pagination
          background
          @current-change="currentChange"
          @size-change="sizeChange"
          layout="sizes, prev, pager, next, jumper, ->, total, slot"
          :total="total"
        >
        </el-pagination>
      </div>
    </div>
    <el-dialog :title="title" :visible.sync="dialogVisible" width="80%">
      <div>
        <el-form :model="emp" :rules="rules" ref="empForm">
          <el-row>
            <el-col :span="10">
              <el-form-item label="车牌号:" prop="enVlp">
                <el-input
                  size="mini"
                  style="width: 150px"
                  prefix-icon="el-icon-edit"
                  v-model="emp.enVlp"
                  placeholder="请输入车牌号"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="13">
              <el-form-item label="车牌颜色:" prop="enVlpc">
                <el-radio-group v-model="emp.enVlpc">
                  <el-radio label="0">蓝色</el-radio>
                  <el-radio label="1">黄色</el-radio>
                  <el-radio label="2">黑色</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="入口站:" prop="entollStationname">
                <el-input
                  size="mini"
                  style="width: 150px"
                  prefix-icon="el-icon-edit"
                  v-model="emp.entollStationname"
                  placeholder="请输入入口站"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="出口站:" prop="extollStationname">
                <el-input
                  size="mini"
                  style="width: 200px"
                  prefix-icon="el-icon-edit"
                  v-model="emp.extollStationname"
                  placeholder="请输入出口站"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10">
              <el-form-item label="出口时间:" prop="exTime">
                <el-date-picker
                  v-model="emp.exTime"
                  size="mini"
                  type="date"
                  value-format="yyyy-MM-dd"
                  style="width: 150px"
                  placeholder="出口时间"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="标记:" prop="reMarks">
                <el-input
                  size="mini"
                  style="width: 150px"
                  prefix-icon="el-icon-edit"
                  v-model="emp.reMarks"
                  placeholder="标记"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="doAddEmp">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
export default {
  name: "ExwBasic",
  data() {
    return {
      searchValue: {
        politicId: null,
        nationId: null,
        jobLevelId: null,
        posId: null,
        engageForm: null,
        departmentId: null,
        beginDateScope: null,
      },
      title: "",
      importDataBtnText: "导入数据",
      importDataBtnIcon: "el-icon-upload2",
      importDataDisabled: false,
      showAdvanceSearchView: false,
      allDeps: [],
      emps: [],
      loading: false,
      popVisible: false,
      popVisible2: false,
      dialogVisible: false,
      total: 0,
      page: 1,
      keyword: "",
      size: 10,
      joblevels: [],
      positions: [],
      options: [
        {
          value: "选项1",
          label: "黄金糕",
        },
        {
          value: "选项2",
          label: "双皮奶",
        },
        {
          value: "选项3",
          label: "蚵仔煎",
        },
        {
          value: "选项4",
          label: "龙须面",
        },
        {
          value: "选项5",
          label: "北京烤鸭",
        },
      ],
      inputDepName: "所属部门",
      emp: {
        enVlp: "黑A12345",
        enVlpc: 2,
        entollStationname: "瓦盆窑",
        extollStationname: "周家",
        exTime: "2021-12-20",
        reMarks: "非清分业务数据",
      },
      defaultProps: {
        children: "children",
        label: "name",
      },
      rules: {},
    };
  },
  mounted() {
    this.initEmps();
    //this.initData();
    //this.initPositions();
  },
  methods: {
    exportData() {
      window.open("/employee/basic/export", "_parent");
    },
    showEditEmpView(data) {
      //this.initPositions();
      this.title = "编辑差异化信息";
      this.emp = data;
      //this.inputDepName = data.department.name;
      this.dialogVisible = true;
    },
    deleteEmp(data) {
      this.$confirm(
        "此操作将永久删除【" + data.name + "】, 是否继续?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          this.deleteRequest("/exwaste/" + data.id).then((resp) => {
            if (resp) {
              this.initEmps();
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消删除",
          });
        });
    },
    doAddEmp() {
      if (this.emp.id) {
        this.$refs["empForm"].validate((valid) => {
          if (valid) {
            this.putRequest("/exwaste/updateExw/", this.emp).then((resp) => {
              if (resp) {
                this.dialogVisible = false;
                this.initEmps();
              }
            });
          }
        });
      } else {
        this.$refs["empForm"].validate((valid) => {
          if (valid) {
            this.postRequest("/exwaste/updateExw/", this.emp).then((resp) => {
              if (resp) {
                this.dialogVisible = false;
                this.initEmps();
              }
            });
          }
        });
      }
    },
    showDepView() {
      this.popVisible = !this.popVisible;
    },
    sizeChange(currentSize) {
      this.size = currentSize;
      this.initEmps();
    },
    currentChange(currentPage) {
      this.page = currentPage;
      this.initEmps("advanced");
    },
    // showAddEmpView() {
    //   this.emptyEmp();
    //   this.title = "添加员工";
    //   //this.getMaxWordID();
    //   this.dialogVisible = true;
    // },
    initEmps(type) {
      this.loading = true;
      let url =
        "/exwaste/querystudentbypage?page=" + this.page + "&size=" + this.size;
      if (type && type == "advanced") {
        if (this.searchValue.politicId) {
          url += "&politicId=" + this.searchValue.politicId;
        }
        if (this.searchValue.nationId) {
          url += "&nationId=" + this.searchValue.nationId;
        }
        if (this.searchValue.jobLevelId) {
          url += "&jobLevelId=" + this.searchValue.jobLevelId;
        }
        if (this.searchValue.posId) {
          url += "&posId=" + this.searchValue.posId;
        }
        if (this.searchValue.engageForm) {
          url += "&engageForm=" + this.searchValue.engageForm;
        }
        if (this.searchValue.departmentId) {
          url += "&departmentId=" + this.searchValue.departmentId;
        }
        if (this.searchValue.beginDateScope) {
          url += "&beginDateScope=" + this.searchValue.beginDateScope;
        }
      } else {
        url += "&enVlp=" + this.keyword;
      }
      this.getRequest(url).then((resp) => {
        this.loading = false;
        if (resp) {
          this.emps = resp.data;
          this.total = resp.total;
        }
      });
    },
  },
};
</script>

<style>
/* 可以设置不同的进入和离开动画 */
/* 设置持续时间和动画函数 */
.slide-fade-enter-active {
  transition: all 0.8s ease;
}

.slide-fade-leave-active {
  transition: all 0.8s cubic-bezier(1, 0.5, 0.8, 1);
}

.slide-fade-enter, .slide-fade-leave-to
        /* .slide-fade-leave-active for below version 2.1.8 */ {
  transform: translateX(10px);
  opacity: 0;
}
</style>
