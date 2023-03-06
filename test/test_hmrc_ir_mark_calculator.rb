# frozen_string_literal: true

require 'minitest/autorun'
require 'hmrc_ir_mark_calculator'

describe HmrcIrMarkCalculator do
  describe 'succesful run' do
    before do
      file = File.open('./test/files/example.xml')
      file_path = File.expand_path(file)

      @calculator = HmrcIrMarkCalculator.new(file_path)
    end

    it 'should be successful' do
      _(@calculator.run).must_equal({ result: 'RnaaojSteAfx2pomwXK9L/8ceVQ=', success: true })
    end
  end
end
