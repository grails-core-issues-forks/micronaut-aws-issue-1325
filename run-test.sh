#!/bin/bash
STACK_NAME=DemoCdk
export API_URL="$(aws cloudformation describe-stacks --stack-name $STACK_NAME --query 'Stacks[0].Outputs[?OutputKey==`ApiUrl`].OutputValue' --output text)"
curl -i $API_URL
