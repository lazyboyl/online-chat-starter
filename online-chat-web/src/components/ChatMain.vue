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
      <Tabs>
        <TabPane label="老三" icon="logo-apple">
          <Layout>
            <Content>
              <div>
                聊天记录
              </div>
            </Content>
            <Footer style="height: 120px;">
              <Input style="width: 100%;" type="textarea" :rows="4" placeholder="请输入聊天消息" />
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
