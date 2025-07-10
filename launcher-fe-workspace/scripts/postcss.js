
const sass = require('sass')
const postcss = require('postcss')
const cssnano = require('cssnano')

const lodash = require('lodash')
const path = require('path')
const fs = require('fs')
const argv = require('yargs').argv;

const CUSTOMERS_FOLDER = path.join('.', 'clienti');
const PROJECT_STYLES = require(path.join('..', CUSTOMERS_FOLDER, 'customers')).PROJECT_STYLES;

// const cwd = process.cwd();
const ROOT_FOLDER = process.cwd();
const BASE_PROJECT_FOLDER = path.join(ROOT_FOLDER, 'projects');
const UNWANTED_FILES = [
    '.gitkeep'
];

// args
if (argv == null || (lodash.isArray(argv) && lodash.isEmpty(argv))) {
    shell.echo(chalk.red(`No args provided!`));
    process.exit(1);
}

const currentProject = argv.project;
const PROJECT_FOLDER = path.join(BASE_PROJECT_FOLDER, currentProject, 'src');

const CONFIG = PROJECT_STYLES[currentProject];

console.log(CONFIG)

const THEMES = lodash.map(CONFIG, one => {
    return {
        src: path.join(...[PROJECT_FOLDER, 'styles', ...one.src]),
        dest: path.join(...[PROJECT_FOLDER, 'assets', ...one.dest])
    }
})

console.log(THEMES)

const THEME = async (one) => {

    console.time(`theme --> ${one.src}`)

    const source = await fs.promises.readFile(one.src)

    const convert = sass.renderSync({
        data: source.toString(),
        sourceMap: false,
        includePaths: [
            '.', 'node_modules',
            path.dirname(one.src)
        ]
    })

    const result = await postcss().use(cssnano()).process(convert.css, { map: false, from: null })

    await fs.promises.mkdir(path.dirname(one.dest), { recursive: true })

    await fs.promises.writeFile(one.dest, result.css)

    await fs.promises.unlink(one.src);

    console.timeEnd(`theme --> ${one.src}`)
    
}


Promise.resolve()
    .then(() => {
        console.time(`all themes`)
    })
    .then(() => {
        return Promise.all(lodash.map(THEMES, THEME))
    })
    .then(() => {
        console.timeEnd(`all themes`)
        process.exit(0)
    })
    .catch((err) => {
        console.error(err)
        process.exit(1)
    })

