'use strict';

var multiSelect = function (element, name, heading, list, height) {
    return new Vue({
        el: element,
        data: {
            selected: [],
            list: list,
            name: name,
            heading: heading,
            height: height || 140
        },
        methods: {
            isChecked: function (id) {
                for (var i = 0; i < this.selected.length; i++) {
                    if (this.selected[i].id === id) {
                        return true;
                    }
                }
                return false;
            },
            addItem: function (id, index) {
                var selectedItem = this.list[index];
                if (this.isChecked(selectedItem.id)) { // if item exists remove it from the selected list
                    for (var i = 0; i < this.selected.length; i++) {
                        if (selectedItem.id === this.selected[i].id) {
                            this.selected.splice(i, 1);
                            break;
                        }
                    }
                } else {
                    this.selected.push(this.list[index]);
                }

            },
            areAllChecked: function () {
                return this.selected.length === this.list.length;
            },
            addAll: function () {
                if (this.list.length === this.selected.length) {
                    this.selected = [];
                } else {
                    this.selected = this.list.slice();
                }
            }
        },
        template: `
        <div class="row">
            <div class="col-md-12" style ="border-radius: 6px;">
                <label>{{heading}}</label>
                <br/>
                <label v-for="(item,index) in selected">
                    {{item.name}}<span v-if="index+1 < selected.length">,</span>
                </label>
                
                <input type="hidden" v-bind:value="selected"/>
                <div class="toggle-content" v-bind:style="{ display: 'block', overflow: 'auto' , 'margin-bottom': '10px' ,height:height + 'px'}">
                    <span style="padding: 5px 0px; margin-bottom:10px;" v-bind:class="{'checkbox':true , 'alert alert-info alert-sm': areAllChecked() }">
                        <label style="width: 100%">
                            <input type="checkbox" v-on:click.self="addAll" v-bind:checked="areAllChecked()" />
                            Select all
                        </label>
                    </span>
                    <span
                        style="padding: 5px 0px; margin-bottom:10px;"
                        v-for="(item,index) in list"
                        
                        v-bind:class="{'checkbox':true , 'alert alert-info alert-sm': isChecked(item.id) }"
                    >
                        <label v-bind:for="item.id+item.name+index" style="width: 100%">
                            <input type="checkbox" v-bind:id="item.id+item.name+index" v-on:click.self="addItem(item.id,index)" v-bind:name="name" v-bind:checked="isChecked(item.id)"/>
                            {{item.name}}
                        </label>
                    </span>
                </div>
            </div>
        </div>`
    })
}