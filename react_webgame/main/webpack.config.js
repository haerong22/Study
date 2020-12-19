const path = require('path'); // 경로 조작하는 모듈
const RefreshWebpackPlugin = require('@pmmmwh/react-refresh-webpack-plugin');


module.exports = {
	name: 'wordrelay-setting',
	mode: 'development', // 실 서비스 에서는 production
	devtool: 'eval', 
	resolve: {
			extensions: ['.js', '.jsx']
	},

	entry: {
			app:['./client'],
	}, // 입력

	module: {
		rules: [{
			test: /\.jsx?/, // js, jsx 파일에 babel-loader적용
			loader: 'babel-loader',
			options: {
				presets: [
					['@babel/preset-env', {
						targets: {
							browsers: ['> 5% in KR','last 2 chrome versions'],
						},
						debug: true,
					}], 
					'@babel/preset-react'
				],
				plugins: [
					'@babel/plugin-proposal-class-properties',
					'react-refresh/babel',
				],
			},
		}], 
	}, // 모듈적용
	
	plugins: [
		new RefreshWebpackPlugin()
	],

	output: {
		path: path.join(__dirname, 'dist'),
		filename: 'app.js',
		publicPath: './dist/',
	}, // 출력

	devServer: {
		publicPath: './dist/',
		hot: true,
	},
};