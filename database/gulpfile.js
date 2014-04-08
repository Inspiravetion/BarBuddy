//import gulp
var gulp = require('gulp');

// include plug-ins
var browserify = require('gulp-browserify');
var stripDebug = require('gulp-strip-debug');
var uglify = require('gulp-uglify');
var sass = require('gulp-sass');
var jasmine = require('gulp-jasmine');

///////////////////////////////////////
//               Tasks               //
///////////////////////////////////////

gulp.task('scripts', function() {

  gulp.src('./dev/scripts/app.js')
    .pipe(browserify())
    // .pipe(stripDebug())
    // .pipe(uglify())
    .pipe(gulp.dest('./prod/'));

});

gulp.task('styles', function() {

  gulp.src(['./dev/styles/*.scss'])
    .pipe(sass())
    .pipe(gulp.dest('./prod/styles/'));

});

gulp.task('tests', function() {

  gulp.src('./dev/scripts/spec/*.js')
    .pipe(jasmine());

});

///////////////////////////////////////
//        Default Build Script       //
///////////////////////////////////////

gulp.task('default', function(){

    //JS Compilation
    gulp.watch('./dev/scripts/*.js', ['scripts', 'tests']);

    //CSS Compilation
    gulp.watch('./dev/styles/*.scss', ['styles']);

});
