# Swagger Inflector Editor

This is a project to bring a stand-alone editor into the [swagger-inflector](https://github.com/swagger-api/swagger-inflector)
project. It bundles the [swagger-editor-bundle](https://github.com/swagger-api/swagger-editor) into
a simple java-based webapp that will save a local definition on successful edit.

### Getting Started
To rebuild, run `mvn package`. The resulting `target/*.war` will be a self-contained
editor and server.

The default destination for saving specifications is in the `src/main/swagger` folder. This can 
be changed by editing the `SpecsController` file.

### Updating the Swagger Editor
To update the `swagger-editor`, simply copy the files from the [swagger-editor/dist](https://github.com/swagger-api/swagger-editor/tree/master/dist)
into the `src/main/webapps` folder of this project and rebuild.

Once updated, the output war file is (shamefully) copied into the `swagger-inflector/scripts/bin/` project folder.

### Understanding the Swagger Editor Plugin
There is a very simple integration with Swagger Editor in this project.

* On load, the editor will load a specification from `http://localhost:8000/v1/specs`. If
that file does not exist, a default specification will be loaded from `src/main/resources/default.yaml`.

* On edit, there is a handler that is triggered when the editor finishes processing changes (`onComplete)`.
In this function, the state of the editor is examined to see if there are any semantic errors. If not (warnings are OK),
the spec will be sent to `localhost:8080/v1/specs` as a HTTP `POST` operation.

* On successful `POST`, the spec will be saved in the default location, as defined in the `SpecsController`

### Modifying this Project
To test modifications to this project _and_ integration with the [swagger-inflector](https://github.com/swagger-api/swagger-inflector)
project, you should:

1. Fork swagger-inflector into your own account
1. Fork swagger-inflector-editor (this repo) into your account
1. Make changes to this project and copy the `war` file into your fork of swagger-inflector in the `scripts/bin` folder 
as `swagger-editor.war`. Push your changes to remote

Now you can run the quick-start curl command against your fork. To pick up the right location of dependent files, you need to
set the `FORK` environment variable:

```bash
export FORK="fehguy/swagger-inflector/issue-346"

curl -sL https://raw.githubusercontent.com/$FORK/setup.sh | project=good-times bash
```

Now the scripts will pull from _your_ fork directly instead of the mainline swagger-api repo