'use strict';


var paginate = function (element, title, url, tr, noDataMessage) {
    var vue = new Vue({
        el: element,
        data: {
            pageValues: [],
            url: url,
            counter: 1,
            title: title,
            data: [],
            columns: [],
            error: false,
            showNext: true,
            showPrev: false,
        },
        mounted: function () {
            var self = this;
            $.ajax({
                url: url,
                method: "post",
                success: function (result) {
                    if (result.status === 'error') {
                        self.error = true;
                        self.counter = 1;
                    } else {
                        self.error = false;
                        self.data = result.data;
                        self.pageValues.push(result.pageValue);
                        self.columns = result.columns;
                        self.showNext = result.more;
                    }
                },
                error: function () {
                    self.error = true;
                    self.counter = 1;
                }
            });
        },
        methods: {
            getPageValue: function () {
                if (this.pageValues.length === 0) {
                    return '';
                } else {
                    return this.pageValues[this.pageValues.length - 1];
                }
            },
            getNext: function () {
                var self = this;
                this.counter += this.data.length;
                $.ajax({
                    url: url,
                    method: "post",
                    data: {
                        pageValue: self.getPageValue()
                    },
                    success: function (result) {
                        if (result.status === 'error') {
                            self.error = true;
                            self.counter = 1;
                        } else {
                            self.error = false;
                            self.data = result.data;
                            self.columns = result.columns;
                            self.showNext = result.more;
                            self.showPrev = true;
                            self.pageValues.push(result.pageValue);
                        }
                    },
                    error: function () {
                        self.error = true;
                        self.counter = 1;
                    }
                });
            },
            getPrev: function () {
                var self = this;
                self.pageValues.pop();
                self.pageValues.pop();
                $.ajax({
                    url: url,
                    method: "post",
                    data: {
                        pageValue: self.getPageValue()
                    },
                    success: function (result) {
                        if (result.status === 'error') {
                            self.error = true;
                            self.counter = 1;
                        } else {
                            self.error = false;
                            self.counter -= result.data.length;
                            self.data = result.data;
                            self.columns = result.columns;
                            self.showNext = result.more;
                            self.pageValues.push(result.pageValue);
                            self.showPrev = self.pageValues.length > 1;

                        }
                    },
                    error: function () {
                        self.error = true;
                        self.counter = 1;
                    }
                });
            }
        },
        template: `
        <div>
           <div class="row">
                <div class="col-md-12">
                    <h4> {{ title }}</h4>
                    <div class="form-group">
                        <div class="col-md-6">
                            <label>Search</label>
                            <input type="text" class = "form-control" placeholder="search">
                            <button class="btn btn-primary btn-sm"> Search </button>
                            <button class = "btn btn-primary btn-sm"> Clear </button>
                        </div>
                    </div>
                    <table class="table table-hover table-striped">
                        <thead>
                            <tr>
                                <th> # </th> 
                                <th v-for="column in columns"> {{column}} </th> 
                            </tr> 
                        </thead>
                        <tbody v-if="error">
                            ` + noDataMessage + `
                        </tbody>
                        <tbody v-else>
                            ` + tr + `    
                        </tbody>
                    </table>
                </div>
           </div>
           <div class = "row">
               <div class = "col-md-12" >
                    <ul class = "pagination pull-right" >
                        <li v-if="showPrev">
                            <button type = "button" class = "mb-xs mt-xs mr-xs btn btn-default" v-on:click="getPrev" >
                                <i class = "fa fa-chevron-left">  </i> 
                                Previous
                            </button> 
                        </li> 
                        <li v-if="showNext">
                            <button type = "button" class = "mb-xs mt-xs mr-xs btn btn-default" v-on:click="getNext" >
                                Next
                                <i class = "fa fa-chevron-right"></i> 
                            </button> 
                        </li> 
                    </ul> 
               </div> 
            </div>
        </div>    
        `
    });
}