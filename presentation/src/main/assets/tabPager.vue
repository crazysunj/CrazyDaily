<template>
    <div>
        <tabPager :titledata="datas" style="flex:1;">

        </tabPager>
    </div>
</template>

<script>
    export default{
        data () {
            return {
                datas: ["Android","iOS","前端"]
            }
        }
    }
</script>