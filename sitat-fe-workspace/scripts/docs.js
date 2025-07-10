'use strict';

const Hapi = require('hapi');
const Path = require('path');
const shell = require('shelljs');

const htdocs = Path.join(__dirname, '..', 'docs');

const server = Hapi.server({
    host: '127.0.0.1',
    port: 3000,
    routes: {
        files: {
            relativeTo: htdocs
        }
    },
    debug: {
        request: ['error']
    }
});

const start = async () => {

    await server.register(require('inert'));

    server.route({
        method: 'GET',
        path: '/{param*}',
        handler: {
            directory: {
                path: '.',
                listing: true
            }
        }
    });

    await server.start();

    shell.echo('Server running at:', server.info.uri);
};

start();

