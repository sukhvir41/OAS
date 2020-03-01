'use strict';

//does seek pgination
var paginate = function (element, title, url, columns, tr, noDataMessage, showSearch, additionalData) {
    var vue = new Vue({
        el: element,
        data: {
            pageValues: [],
            url: url,
            title: title,
            data: [],
            counter: 0, // used to show the # columnn in the table
            columns: columns,
            error: false,
            showNext: false,
            showPrev: false,
            searchText: '',
            showSearch: showSearch || true,
            additionalData: additionalData || "",
        },
        watch: {
            searchText: function (val) {
                this.reserData();
                this.counter += (this.data.length <= 0) ? 1 : this.data.length;
                var self = this;
                this.disableButtons();
                $.ajax({
                    url: self.url,
                    method: "post",
                    data: {
                        searchText: self.searchText,
                        additionalData: self.additionalData,
                    },
                    success: function (result) {
                        if (result.status === 'error' || result.data.length <= 0) {
                            this.processError();
                        } else {
                            self.data = result.data;
                            self.pageValues.push(result.pageValue);
                            self.showNext = result.more;
                        }
                    },
                    error: function () {
                        self.processError();
                    }
                });
            }
        },
        mounted: function () {
            this.getNext();
        },
        methods: {

            disableButtons: function () {
                this.showPrev = false;
                this.showNext = false;
            },

            processError: function () {
                this.error = true;
                this.reserData();
                this.disableButtons();
            },

            reserData: function () {
                this.pageValues = [];
                this.showPrev = false;
                this.counter = 0;
            },

            getPageValue: function () {
                if (this.pageValues.length === 0) {
                    return '';
                } else {
                    return this.pageValues[this.pageValues.length - 1];
                }
            },

            getNext: function () {
                this.counter += (this.data.length <= 0) ? 1 : this.data.length;
                var self = this;
                this.disableButtons();
                $.ajax({
                    url: self.url,
                    method: "post",
                    data: {
                        pageValue: self.getPageValue(),
                        searchText: self.searchText,
                        additionalData: additionalData,
                    },
                    success: function (result) {
                        if (result.status === 'error' || result.data.length <= 0) {
                            self.processError();
                        } else {

                            self.showNext = result.more;
                            self.showPrev = self.data.length > 0;
                            self.data = result.data;
                            self.pageValues.push(result.pageValue);
                        }
                    },
                    error: function () {
                        self.processError();
                    }
                });
            },

            getPrev: function () {
                var self = this;
                self.pageValues.pop();
                self.pageValues.pop();
                this.disableButtons();
                $.ajax({
                    url: self.url,
                    method: "post",
                    data: {
                        pageValue: self.getPageValue(),
                        searchText: self.searchText,
                        additionalData: self.additionalData,
                    },
                    success: function (result) {
                        if (result.status === 'error' || result.data.length <= 0) {
                            self.processError();
                        } else {
                            self.counter -= result.data.length;
                            self.data = result.data;
                            self.showNext = result.more;
                            self.showPrev = self.pageValues.length > 0;
                            self.pageValues.push(result.pageValue);
                        }
                    },
                    error: function () {
                        self.processError();
                    }
                });
            }
        },

        template: `
        <div>
           <div class="row">
                <div class="col-md-12">
                    <div class="row">
                        <div class = "col-md-4 col-xs-12">
                            <h4> 
                                {{title}}     
                            </h4>
                            <input v-if="showSearch" type="text" class = "form-control" placeholder = "search" v-model="searchText" />
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
                        <li v-if="error">
                            <button type = "button" class = "mb-xs mt-xs mr-xs btn btn-default" v-on:click = "getNext" >
                                <i class = "fa fa-refresh"> 
                                </i> 
                            </button> 
                        </li>
                        <li v-if="showPrev">
                            <button type = "button" class = "mb-xs mt-xs mr-xs btn btn-default" v-on:click="getPrev" >
                                <i class = "fa fa-chevron-left">  </i> 
                                Previous
                            </button> 
                        </li>
                        <li v-else>
                            <button type = "button" class = "mb-xs mt-xs mr-xs btn btn-default disabled" >
                                <i class = "fa fa-chevron-left"></i>
                                Previous
                            </button>
                        </li> 
                        <li v-if="showNext">
                            <button type = "button" class = "mb-xs mt-xs mr-xs btn btn-default" v-on:click="getNext" >
                                Next
                                <i class = "fa fa-chevron-right"></i> 
                            </button> 
                        </li>
                        <li v-else>
                            <button type = "button" class = "mb-xs mt-xs mr-xs btn btn-default disabled" >
                                Next 
                                <i class = "fa fa-chevron-right"> </i>  
                            </button>  
                        </li>  
                    </ul> 
               </div> 
            </div>
        </div>    
        `
    });
}