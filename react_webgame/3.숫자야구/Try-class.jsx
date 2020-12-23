import React, { PureComponent } from 'react';

class Try extends PureComponent {
    constructor(props) {
        super(props)
        
        const filtered = this.props.filtered(() => {
            
        })
        this.state = {
            result: filtered,
            try: this.props.try,
        }
    }
    
    render() {
        const { tryInfo } = this.props;
        return (
            <li>
                <div>{tryInfo.try}</div>
                <div>{tryInfo.result}</div>    
            </li>
        )
    }
}

export default Try;