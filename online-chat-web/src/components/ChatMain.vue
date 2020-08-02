<template>
  <div class="layout">
    <Layout>
      <Sider hide-trigger :style="{background: '#fff'}" width="250">
        <Tabs style="min-height: 300px;">
          <TabPane label="消息" icon="logo-apple">标签一的内容</TabPane>
          <TabPane label="联系人" icon="logo-windows">
            <div>
              <Button type="text" size="small">好友</Button>
              <Button type="text" size="small">群聊</Button>
              <Button type="text" size="small" style="float: right;" icon="md-add"></Button>
            </div>
            <div style="margin-top: 5px;margin-left: 5px;">
              <Tree :data="friendData"></Tree>
            </div>
          </TabPane>
        </Tabs>
      </Sider>
      <Tabs style="width: 50%;">
        <TabPane label="老三" icon="logo-apple">
          <Layout>
            <Content style="margin-left: 20px;">
              <Scroll>
                <List>
                  <li data-v-609bc0e1="" class="ivu-list-item" >
                    <div data-v-609bc0e1="" class="ivu-list-item-meta">
                      <div class="ivu-list-item-meta-avatar"><span
                        class="ivu-avatar ivu-avatar-circle ivu-avatar-image ivu-avatar-default"><img
                        src="https://dev-file.iviewui.com/userinfoPDvn9gKWYihR24SpgC319vXY8qniCqj4/avatar"></span></div>
                      <div class="ivu-list-item-meta-content" >
                        <div class="ivu-list-item-meta-title" style="text-align: center;">2020/08/08 22:10:30</div>
                        <div class="ivu-list-item-meta-description" style="text-align: justify;">你好啊，在做什么呢？</div>
                      </div>
                    </div> <!---->
                    <div class="ivu-list-item-extra"></div>
                  </li>
                  <li data-v-609bc0e1="" class="ivu-list-item" >
                    <div data-v-609bc0e1="" class="ivu-list-item-meta">
                      <div class="ivu-list-item-meta-avatar"><span
                        class="ivu-avatar ivu-avatar-circle ivu-avatar-image ivu-avatar-default"><img
                        src="https://dev-file.iviewui.com/userinfoPDvn9gKWYihR24SpgC319vXY8qniCqj4/avatar"></span></div>
                      <div class="ivu-list-item-meta-content" >
                        <div class="ivu-list-item-meta-title" style="text-align: center;">2020/08/08 22:15:30</div>
                        <div class="ivu-list-item-meta-description" style="text-align: justify;">
                          <div class="ivu-tooltip-dark"  x-placement="right-end">
                            <div class="ivu-tooltip-content">
                              <div class="ivu-tooltip-arrow"></div>
                              <div class="ivu-tooltip-inner ivu-tooltip-inner-with-width" >咋不回复我呢？咋不回复我呢？咋不回复我呢？咋不回复我呢？咋不回复我呢？咋不回复我呢？咋不回复我呢？咋不回复我呢？咋不回复我呢？咋不回复我呢？咋不回复我呢？咋不回复我呢？咋不回复我呢？咋不回复我呢？咋不回复我呢？咋不回复我呢？咋不回复我呢？咋不回复我呢？</div>
                            </div>
                          </div>
                        </div>
                      </div>
                    </div> <!---->
                    <div class="ivu-list-item-extra"></div>
                  </li>
                  <li data-v-609bc0e1="" class="ivu-list-item" >
                    <div data-v-609bc0e1="" class="ivu-list-item-meta">
                      <div class="ivu-list-item-meta-content" >
                        <div class="ivu-list-item-meta-title" style="text-align: center;">2020/08/08 22:19:30</div>
                        <div class="ivu-list-item-meta-description" style="text-align: right;">
                          <div style="text-align: left;display: inline-block;">在的呢！</div>
                        </div>
                      </div>
                      <div class="ivu-list-item-meta-avatar"><span
                        class="ivu-avatar ivu-avatar-circle ivu-avatar-image ivu-avatar-default"><img
                        src="https://dev-file.iviewui.com/userinfoPDvn9gKWYihR24SpgC319vXY8qniCqj4/avatar"></span></div>
                    </div> <!---->
                    <div class="ivu-list-item-extra"></div>
                  </li>
                  <li data-v-609bc0e1="" class="ivu-list-item" >
                    <div data-v-609bc0e1="" class="ivu-list-item-meta">
                      <div class="ivu-list-item-meta-content" >
                        <div class="ivu-list-item-meta-title" style="text-align: center;">2020/08/08 22:19:31</div>
                        <div class="ivu-list-item-meta-description" style="text-align: right;">
                          <div style="text-align: left;display: inline-block;">
                            <div class="ivu-tooltip-dark"  x-placement="right-end" style="background-color: teal;">
                              <div class="ivu-tooltip-content">
                                <div class="ivu-tooltip-arrow"></div>
                                <div class="ivu-tooltip-inner ivu-tooltip-inner-with-width" >在的呢！你在做什么呢？在的呢！你在做什么呢？在的呢！你在做什么呢？在的呢！你在做什么呢？在的呢！你在做什么呢？在的呢！你在做什么呢？在的呢！你在做什么呢？在的呢！你在做什么呢？在的呢！你在做什么呢？在的呢！你在做什么呢？在的呢！你在做什么呢？在的呢！你在做什么呢？在的呢！你在做什么呢？在的呢！你在做什么呢？在的呢！你在做什么呢？在的呢！你在做什么呢？在的呢！你在做什么呢？</div>
                              </div>
                            </div>
                          </div>
                        </div>
                      </div>
                      <div class="ivu-list-item-meta-avatar"><span
                        class="ivu-avatar ivu-avatar-circle ivu-avatar-image ivu-avatar-default"><img
                        src="https://dev-file.iviewui.com/userinfoPDvn9gKWYihR24SpgC319vXY8qniCqj4/avatar"></span></div>
                    </div> <!---->
                    <div class="ivu-list-item-extra"></div>
                  </li>
                </List>
              </Scroll>
            </Content>
            <Footer style="height: 120px;">
              <Input style="width: 100%;" type="textarea" :rows="4" placeholder="请输入聊天消息"/>
            </Footer>
          </Layout>
        </TabPane>
        <TabPane label="啊勋" icon="logo-windows">标签二的内容</TabPane>
        <TabPane label="9级宝石" icon="logo-tux">标签三的内容</TabPane>
      </Tabs>
    </Layout>
  </div>
</template>

<script>
export default {
  name: 'chatMain',
  data () {
    return {
      friendData: [
        {
          title: '我的好友',
          expand: false,
          children: [
            {
              title: '9级宝石'
            },
            {
              title: '大斌'
            }
          ]
        },
        {
          title: '同学',
          expand: false,
          children: [
            {
              title: '老三'
            },
            {
              title: '啊勋'
            },
            {
              title: '大伟'
            },
            {
              title: '决战man'
            },
            {
              title: '决战boy'
            },
            {
              title: '无敌小伙子'
            }
          ]
        }
      ]
    }
  }
}
</script>

<style scoped>
  .layout {
    border: 1px solid #d7dde4;
    background: #f5f7f9;
    position: relative;
    border-radius: 4px;
    overflow: hidden;
    height: 100%;
  }
</style>
