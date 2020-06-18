package org.andstatus.todoagenda.task.astrid;

import android.net.Uri;

public interface TaskSource {

  TaskSource GOOGLE_TASKS = new TaskSource() {
    @Override
    public Uri getListUri() {
      return AstridCloneTasksProvider.GOOGLE_LISTS_URI;
    }

    @Override
    public String getListColumnId() {
      return "gtl_id";
    }

    @Override
    public String getListColumnTitle() {
      return "gtl_title";
    }

    @Override
    public String getListColumnListColor() {
      return "gtl_color";
    }

    @Override
    public String getListColumnAccount() {
      return "gtl_account";
    }
  };
  TaskSource TASKS = new TaskSource() {
    @Override
    public Uri getListUri() {
      return AstridCloneTasksProvider.TASKS_LISTS_URI;
    }

    @Override
    public String getListColumnId() {
      return "cdl_id";
    }

    @Override
    public String getListColumnTitle() {
      return "cdl_name";
    }

    @Override
    public String getListColumnListColor() {
      return "cdl_color";
    }

    @Override
    public String getListColumnAccount() {
      return "cda_name";
    }
  };

  Uri getListUri();

  String getListColumnId();

  String getListColumnTitle();

  String getListColumnListColor();

  String getListColumnAccount();
}