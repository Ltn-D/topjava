const mealAjaxUrl = "profile/meals/";

// https://stackoverflow.com/a/5064235/548473
const ctx = {
    ajaxUrl: mealAjaxUrl,
    updateTable: function () {
        $.ajax({
            type: "GET",
            url: mealAjaxUrl + "filter",
            data: $("#filter").serialize()
        }).done(updateTableByData);
    }
};

function clearFilter() {
    $("#filter")[0].reset();
    $.get(mealAjaxUrl, updateTableByData);
}

$(function () {
    makeEditable(
        $("#datatable").DataTable({
            "ajax": {
                "url": mealAjaxUrl,
                "dataSrc": ""
            },
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime",
                    "render": function (date, type, row) {
                        if (type === "display") {
                            return prepareDateTime(date);
                        }
                        return date;
                    }
                },
                {
                    "data": "description",
                    "render": function (date, type, row) {
                        return date;
                    }

                },
                {
                    "data": "calories",
                    "render": function (date, type, row) {
                        return date;
                    }
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderEditBtn
                },
                {
                    "orderable": false,
                    "defaultContent": "",
                    "render": renderDeleteBtn
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ],
            "createdRow": function (row, data, dataIndex) {
                if (data.excess) {
                    $(row).attr("data-meal-excess", true);
                } else {
                    $(row).attr("data-meal-excess", false);
                }
            }

        })
    );
});
$(function () {

    const locale = $('html').attr('lang') || 'ru';
    $.datetimepicker.setLocale(locale);

    const dateTimeField = $('#dateTime');
    if (dateTimeField.length) {
        dateTimeField.datetimepicker({
            format: 'Y-m-d H:i'
        });
    }

    const startDate = $('#startDate');
    const endDate = $('#endDate');
    if (startDate.length || endDate.length) {
        $('#startDate, #endDate').datetimepicker({
            timepicker: false,
            format: 'Y-m-d'
        });
    }

    const startTime = $('#startTime');
    const endTime = $('#endTime');
    if (startTime.length || endTime.length) {
        $('#startTime, #endTime').datetimepicker({
            datepicker: false,
            format: 'H:i'
        });
    }
});