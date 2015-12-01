'use strict';

angular.module('app')
  .directive('portalDatepicker', function () {
    return {
      restrict: 'A',
      scope: {
      	date: '='
      },
      link: function postLink(scope, element, attrs) {
        element.datepicker({
    		  format: "yyyy-mm-dd",
    		  language: "zh-CN"
		    })
        .on('changeDate',function(event){
          var originDate = element.datepicker('getDate');
          scope.date = originDate.getTime();
        });
		    element.datepicker('setDate', new Date(scope.date));
      }
    };
  });
